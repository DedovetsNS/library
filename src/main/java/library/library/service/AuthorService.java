package library.library.service;

import library.library.model.Author;
import library.library.model.Book;
import library.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookService bookService;

    boolean existsByName(String authorName) {
        if (authorRepository.existsByName(authorName)) {
            return true;
        } else return false;
    }

    Author getAuthorByName(String authorName) {
        return authorRepository.findByName(authorName);
    }

    public Author addAuthor(Author author) {
        if (authorRepository.existsByName(author.getName())) {
            throw new IllegalArgumentException("This authors already exists in the database.");
        } else {
            return authorRepository.save(author);
        }
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public void addBookToAuthor(Book book, String authorName) {
        Author author = authorRepository.findByName(authorName);
        author.getBooks().add(book);
        authorRepository.save(author);
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public Optional<Author> findById(Long id) {
       return authorRepository.findById(id);
    }
}