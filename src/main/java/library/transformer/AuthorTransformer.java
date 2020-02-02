package library.transformer;

import library.dto.AuthorDto;
import library.dto.AuthorInBookDto;
import library.dto.BookInAuthorDto;
import library.model.Author;
import library.model.Book;
import library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthorTransformer {

    private final BookRepository bookRepository;
    private  BookTransformer bookTransformer;


    @Autowired
    public AuthorTransformer( BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setBookTransformer(BookTransformer bookTransformer) {
        this.bookTransformer = bookTransformer;
    }

    public Author toAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setBirthday(authorDto.getBirthday());
        return author;
    }

    public AuthorDto toAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        Set<Book> books =  bookRepository.findBooksInAuthor(author.getId());
        Set<BookInAuthorDto> booksInAuthorDto = bookTransformer.toBookInAuthorDto(books);
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBirthday(author.getBirthday());
        authorDto.setBooks(booksInAuthorDto);
        return authorDto;
    }

    public Set<AuthorDto> toAuthorDto(Set<Author> authors) {
        Set<AuthorDto> authorsDto = new HashSet<>();
        authors.forEach(author -> authorsDto.add(toAuthorDto(author)));
        return authorsDto;
    }

    public AuthorInBookDto toAuthorInBookDto (Author author) {
        return new AuthorInBookDto(author.getId(), author.getName(), author.getBirthday());
    }

    public Set<AuthorInBookDto> toAuthorInBookDto(Collection<Author> authors){
        Set<AuthorInBookDto> authorsInBookDto = new HashSet<>();
        for (Author author : authors) {
            authorsInBookDto.add(toAuthorInBookDto(author));
        }
        return authorsInBookDto;
    }

    public AuthorDto toAuthorDto(AuthorInBookDto author) {
        AuthorDto authorDto = new AuthorDto();
        Set<BookInAuthorDto> booksInAuthorDto = new HashSet<>();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBirthday(author.getBirthday());
        authorDto.setBooks(booksInAuthorDto);
        return authorDto;
    }




}
