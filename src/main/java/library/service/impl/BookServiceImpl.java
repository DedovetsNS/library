package library.service.impl;

import library.dto.AuthorDto;
import library.dto.AuthorInBookDto;
import library.dto.BookDto;
import library.exception.AlreadyExistException;
import library.exception.BadRequestParametrException;
import library.exception.NotAllAuthorsDataException;
import library.exception.NotFoundException;
import library.model.Book;
import library.repository.AuthorRepository;
import library.repository.BookAuthorRepository;
import library.repository.BookRepository;
import library.repository.LoanRepository;
import library.service.AuthorService;
import library.service.BookAuthorService;
import library.service.BookService;
import library.transformer.impl.AuthorTransformer;
import library.transformer.impl.BookTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final AuthorTransformer authorTransformer;
    private final AuthorService authorService;
    private final BookTransformer bookTransformer;
    private final BookAuthorService bookAuthorService;
    private final LoanRepository loanRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           BookAuthorRepository bookAuthorRepository,
                           AuthorTransformer authorTransformer,
                           AuthorService authorService,
                           BookTransformer bookTransformer,
                           BookAuthorService bookAuthorService,
                           LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
        this.authorTransformer = authorTransformer;
        this.authorService = authorService;
        this.bookTransformer = bookTransformer;
        this.bookAuthorService = bookAuthorService;
        this.loanRepository = loanRepository;
    }

    @Transactional
    @Override
    public Set<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByName(String bookName) {
        return bookRepository.findByName(bookName).orElseThrow(() -> new NotFoundException("Book", "name", bookName));
    }

    @Transactional
    @Override
    public BookDto add(BookDto bookDto) {
        String name = bookDto.getName();

        if (bookRepository.existsByName(name)) {
            throw new AlreadyExistException("Book", "name", name);
        }
        Set<Long> authorsToSaveId = new HashSet<>();

        Set<AuthorInBookDto> authorsToSave = bookDto.getAuthors();
        for (AuthorInBookDto author : authorsToSave) {
            if (authorRepository.existsByName(author.getName())) {
                authorsToSaveId.add(authorRepository.findByName(author.getName()).get().getId());
            } else if (author.getName() == null || author.getBirthday() == null) {
                throw new NotAllAuthorsDataException();
            } else {
                AuthorDto authorDto = new AuthorDto();
                authorDto.setName(author.getName());
                authorDto.setBirthday(author.getBirthday());
                authorDto = authorService.add(authorDto);
                authorsToSaveId.add(authorDto.getId());
            }
        }
        Book book = bookRepository.save(bookTransformer.toEntity(bookDto));
        bookAuthorService.saveAuthorsInBook(authorsToSaveId, book.getId());
        return bookTransformer.toDto(book);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book", "id", id.toString()));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id);
        if (loanRepository.existsByBookId(id)) {
            throw new BadRequestParametrException("Not allowed delete a book that is loaned.");
        }
        bookRepository.deleteById(id);
        bookAuthorRepository.deleteAllByBookId(id);
    }

    @Override
    public boolean existByName(String name) {
        return bookRepository.existsByName(name);
    }

    @Override
    public Integer getQuantityInStockByName(String name) {
        return bookRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Book", "name", name))
                .getInStockQuantity();
    }

    @Transactional
    @Override
    public BookDto update(BookDto bookDto) {
        String name = bookDto.getName();

        if (existByName(name) &&
                (bookRepository.findByName(name).get().getId().equals(bookDto.getId()))) {
            throw new AlreadyExistException("Book", "name", bookDto.getName());
        }
        Long bookId = bookDto.getId();
        Book updatableBook = findById(bookId);

        updatableBook.setName(bookDto.getName());
        updatableBook.setPublisher(bookDto.getPublisher());
        updatableBook.setTotalQuantity(bookDto.getTotalQuantity());
        updatableBook.setInStockQuantity(bookDto.getInStockQuantity());

        bookAuthorRepository.deleteAllByBookId(bookId);

        Set<AuthorInBookDto> authorInBookDto = bookDto.getAuthors();
        for (AuthorInBookDto author : authorInBookDto) {
            if (authorRepository.existsByName(author.getName())) {
                Long authorId = authorRepository.findByName(author.getName()).get().getId();
                bookAuthorService.saveAuthorInBook(authorId, bookId);
            } else {
                AuthorDto authorDto = authorTransformer.toDto(author);
                authorDto = authorService.add(authorDto);
                bookAuthorService.saveAuthorInBook(authorDto.getId(), bookId);
            }
        }
        return bookTransformer.toDto(bookRepository.save(updatableBook));
    }

    @Transactional
    @Override
    public void takeBookToLoan(String bookName, Integer quantity) {
        if (!existByName(bookName)) {
            throw new NotFoundException("Book", "name", bookName);
        }
        if (getQuantityInStockByName(bookName) < quantity) {
            throw new BadRequestParametrException("Less books in the library than want to take.");
        }
        Book takedBook = bookRepository.findByName(bookName).orElseThrow(() -> new NotFoundException("Book", "name", bookName));
        Integer newQuantity = takedBook.getInStockQuantity() - quantity;
        takedBook.setInStockQuantity(newQuantity);
        bookRepository.save(takedBook);
    }

    @Transactional
    @Override
    public void returnBook(String bookName, Integer quantity) {
        Book takedBook = bookRepository.findByName(bookName).orElseThrow(() -> new NotFoundException("Book", "name", bookName));
        Integer newQuantity = takedBook.getInStockQuantity() + quantity;
        takedBook.setInStockQuantity(newQuantity);
        bookRepository.save(takedBook);
    }
}
