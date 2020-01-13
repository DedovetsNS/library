package library.library;

import library.library.dto.AuthorInBookDto;
import library.library.dto.BookDto;
import library.library.dto.BookRequestDto;
import library.library.model.Author;
import library.library.model.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Transformer {

    public Book fromBookDtoToBook(BookDto bookDto) {
        Book book = new Book();
        List<Author> authors = new ArrayList<>();

        for (AuthorInBookDto authorDto : bookDto.getAuthors()) {
            Author author = new Author(authorDto.getName(), authorDto.getBirthday());
            author.setBooks(new ArrayList<Book>());
            author.getBooks().add(book);
            authors.add(author);
        }

        book.setName(bookDto.getName());
        book.setPublisher(bookDto.getPublisher());
        book.setTotalQuantity(bookDto.getTotalQuantity());
        book.setIn_stock_quantity(bookDto.getIn_stock_quantity());
        book.setAuthors(authors);

        return book;
    }
}
