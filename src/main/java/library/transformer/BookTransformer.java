package library.transformer;

import library.dto.AuthorInBookDto;
import library.dto.BookDto;
import library.model.Author;
import library.model.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BookTransformer {

    public Book toBook(BookDto bookDto) {
        Book book = new Book();
        List<Author> authors = new ArrayList<>();

        for (AuthorInBookDto authorDto : bookDto.getAuthors()) {
            Author author = new Author();
            author.setName(authorDto.getName());
            author.setBirthday(authorDto.getBirthday());
            author.setBooks(new ArrayList<Book>());
            author.getBooks().add(book);
            authors.add(author);
        }
        book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setPublisher(bookDto.getPublisher());
        book.setTotalQuantity(bookDto.getTotalQuantity());
        book.setInStockQuantity(bookDto.getInStockQuantity());
        book.setAuthors(authors);

        return book;
    }

    public BookDto toBookDto(Book book) {
        BookDto bookDto = new BookDto();
        Collection<Author> authors = book.getAuthors();
        List<AuthorInBookDto> authorsInBookDto = new ArrayList<>();

        for (Author author : authors) {
            authorsInBookDto.add(new AuthorInBookDto(author.getId(), author.getName(), author.getBirthday()));
        }

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
}
