package library.transformer;

import library.dto.AuthorDto;
import library.dto.BookInAuthorDto;
import library.model.Author;
import library.model.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class AuthorTransformer {

    public Author toAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setBirthday(authorDto.getBirthday());
        return author;
    }

    public AuthorDto toAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        List<BookInAuthorDto> booksInAuthorDto = new ArrayList<>();
        Collection<Book> books = author.getBooks();

        if (books != null) {
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

    public List<AuthorDto> toAuthorDto(List<Author> authors) {
        List<AuthorDto> authorsDto = new ArrayList<>();
        authors.forEach(author -> authorsDto.add(toAuthorDto(author)));
        return authorsDto;
    }
}
