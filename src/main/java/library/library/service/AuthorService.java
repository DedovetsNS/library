package library.library.service;

import library.library.model.Author;
import library.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private  AuthorRepository authorRepository;

    boolean existsByName(String authorName) {
        if (authorRepository.existsByName(authorName)) {
            return true;
        } else {
            return false;
        }
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
}
