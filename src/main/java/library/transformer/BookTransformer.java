package library.transformer;

import library.dto.AuthorInBookDto;
import library.dto.BookDto;
import library.dto.BookInAuthorDto;
import library.model.Author;
import library.model.Book;
import library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookTransformer {

    private final AuthorRepository authorRepository;
    private  AuthorTransformer authorTransformer;

    @Autowired
    public BookTransformer(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setAuthorTransformer(AuthorTransformer authorTransformer) {
        this.authorTransformer = authorTransformer;
    }

    public Book toBook(BookDto bookDto) {
        Book book = new Book();

        book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setPublisher(bookDto.getPublisher());
        book.setTotalQuantity(bookDto.getTotalQuantity());
        book.setInStockQuantity(bookDto.getInStockQuantity());
        return book;
    }

    public BookDto toBookDto(Book book) {
        BookDto bookDto = new BookDto();
        Set<Author> authors = authorRepository.findAuthorsOfBook(book.getId());
        Set<AuthorInBookDto> authorsInBookDto = authorTransformer.toAuthorInBookDto(authors);

        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setPublisher(book.getPublisher());
        bookDto.setAuthors(authorsInBookDto);
        bookDto.setTotalQuantity(book.getTotalQuantity());
        bookDto.setInStockQuantity(book.getInStockQuantity());
        return bookDto;
    }

    public List<BookDto> toBookDto(List<Book> allBooks) {
        List<BookDto> allBooksDto = new ArrayList<>();

        for (Book book : allBooks) {
            BookDto bookDto = toBookDto(book);
            allBooksDto.add(bookDto);
        }
        return allBooksDto;
    }

    private BookInAuthorDto toBookInAuthorDto(Book book) {
        BookInAuthorDto bookInAuthorDto = new BookInAuthorDto();
        bookInAuthorDto.setId(book.getId());
        bookInAuthorDto.setName(book.getName());
        bookInAuthorDto.setPublisher(book.getPublisher());
        return bookInAuthorDto;
    }

    Set<BookInAuthorDto> toBookInAuthorDto(Collection<Book> books){
        Set<BookInAuthorDto> bookInAuthorDtos = new HashSet<>();
        for (Book book : books) {
            bookInAuthorDtos.add(toBookInAuthorDto(book));
        }
        return bookInAuthorDtos;
    }

}
