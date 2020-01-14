package library.library.transformer;

import library.library.dto.AuthorDto;
import library.library.dto.AuthorInBookDto;
import library.library.dto.BookDto;
import library.library.dto.BookInAuthorDto;
import library.library.model.Author;
import library.library.model.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
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

    public BookDto fromBookToBookDto(Book book) {
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
        bookDto.setIn_stock_quantity(book.getIn_stock_quantity());

        return bookDto;
    }

    public Author fromAuthorDtoToAuthor(AuthorDto authorDto) {
        Author author = new Author();

        author.setName(authorDto.getName());
        author.setBirthday(authorDto.getBirthday());

        return author;
    }

    public List<BookDto> fromBookListToBookDtoList(List<Book> allBooks) {
        List<BookDto> allBooksDto = new ArrayList<>();

        for (Book book : allBooks) {
            BookDto bookDto = fromBookToBookDto(book);
            allBooksDto.add(bookDto);
        }
        return allBooksDto;
    }

    public AuthorDto fromAuthorToAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        List<BookInAuthorDto> booksInAuthorDto = new ArrayList<>();
        Collection<Book> books = author.getBooks();

        if(books!=null) {
            for (Book book : books) {
                booksInAuthorDto.add(new BookInAuthorDto(book.getId(), book.getName(), book.getPublisher()));
            }
        }
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBirthday(author.getBirthday());
        authorDto.setBooks(booksInAuthorDto);
        return authorDto;
    }

    public List<AuthorDto> fromAuthorListToAuthorDtoList(List<Author> authors) {
        List<AuthorDto> authorsDto = new ArrayList<>();
        authors.forEach(author -> authorsDto.add(fromAuthorToAuthorDto(author)));
        return authorsDto;
    }
}

