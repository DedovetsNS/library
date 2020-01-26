package library.service.impl;

import library.exception.*;
import library.model.Author;
import library.model.Book;
import library.repository.BookRepository;
import library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorServiceImpl authorService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorServiceImpl authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Transactional
    @Override
    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new EmptyDataBaseException("Books");
        }
        return books;
    }

    @Override
    public Book findByName(String bookName) {
        return bookRepository.findByName(bookName);
    }

    @Transactional
    @Override
    public Book add(Book book) {
        Collection<Author> authors = book.getAuthors();
        String name = book.getName();

        if (bookRepository.existsByName(name)) {
            throw new AlreadyExistByNameException("Book", name);
        }

        List<Author> authorsToSave = new ArrayList<>();
        for (Author author : authors) {
            Author existingAuthor;
            if (authorService.existsByName(author.getName())) {
                existingAuthor = authorService.findByName(author.getName());
                existingAuthor.getBooks().add(book);
                authorsToSave.add(existingAuthor);
            } else if (author.getName() == null || author.getBirthday() == null) {
                throw new NotAllAuthorsDataException();
            } else {
                authorsToSave.add(author);
            }
        }
        book.setInStockQuantity(book.getTotalQuantity());
        book.setAuthors(authorsToSave);
        return bookRepository.save(book);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundByIdException("Book", id));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Book book = findById(id);
        book.setAuthors(null);
        bookRepository.save(book);
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existByName(String name) {
        return bookRepository.existsByName(name);
    }

    @Override
    public Integer getQuantityInStockByName(String name) {
        return bookRepository.getBookByName(name).getInStockQuantity();
    }

    @Transactional
    @Override
    public Book update(Book book) {
        if (existByName(book.getName())) {
            throw new AlreadyExistByNameException("Book", book.getName());
        }
        Book updatableBook = findById(book.getId());

        updatableBook.setName(book.getName());
        updatableBook.setPublisher(book.getPublisher());
        updatableBook.setTotalQuantity(book.getTotalQuantity());
        updatableBook.setInStockQuantity(book.getInStockQuantity());
        updatableBook.setAuthors(book.getAuthors());
        return bookRepository.save(updatableBook);
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
        Book takedBook = bookRepository.getBookByName(bookName);
        Integer newQuantity = takedBook.getInStockQuantity() - quantity;
        takedBook.setInStockQuantity(newQuantity);
        bookRepository.save(takedBook);
    }

    @Transactional
    @Override
    public void returnBook(String bookName, Integer quantity) {
        Book takedBook = bookRepository.getBookByName(bookName);
        Integer newQuantity = takedBook.getInStockQuantity() + quantity;
        takedBook.setInStockQuantity(newQuantity);
        bookRepository.save(takedBook);
    }

    @Transactional
    @Override
    public void removeAuthor(Book book, Author deletedAuthor) {
        book.getAuthors().remove(deletedAuthor);
    }
}
