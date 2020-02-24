package library.transformer.impl;

import library.dto.AuthorDto;
import library.dto.AuthorInBookDto;
import library.dto.BookInAuthorDto;
import library.model.Author;
import library.model.Book;
import library.repository.BookRepository;
import library.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthorTransformer implements Transformer<Author,AuthorDto> {

    private final BookRepository bookRepository;
    private BookTransformer bookTransformer;

    @Autowired
    public AuthorTransformer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setBookTransformer(BookTransformer bookTransformer) {
        this.bookTransformer = bookTransformer;
    }

    @Override
    public Author toEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setBirthday(authorDto.getBirthday());
        return author;
    }


    @Override
    public AuthorDto toDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        Set<Book> books = bookRepository.findBooksInAuthor(author.getId());
        Set<BookInAuthorDto> booksInAuthorDto = bookTransformer.toBookInAuthorDto(books);
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBirthday(author.getBirthday());
        authorDto.setBooks(booksInAuthorDto);
        return authorDto;
    }

    private AuthorInBookDto toAuthorInBookDto(Author author) {
        return new AuthorInBookDto(author.getId(), author.getName(), author.getBirthday());
    }

    Set<AuthorInBookDto> toAuthorInBookDto(Collection<Author> authors) {
        Set<AuthorInBookDto> authorsInBookDto = new HashSet<>();
        for (Author author : authors) {
            authorsInBookDto.add(toAuthorInBookDto(author));
        }
        return authorsInBookDto;
    }

    public AuthorDto toDto(AuthorInBookDto author) {
        AuthorDto authorDto = new AuthorDto();
        Set<BookInAuthorDto> booksInAuthorDto = new HashSet<>();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBirthday(author.getBirthday());
        authorDto.setBooks(booksInAuthorDto);
        return authorDto;
    }
}
