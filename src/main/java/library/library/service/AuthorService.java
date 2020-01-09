package library.library.service;

import library.library.model.Author;
import library.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    boolean existsByName(String authorName) {
        if (authorRepository.existsByName(authorName)) {
            return true;
        } else return false;
    }

    Author getAuthorByName(String authorName) {
        return authorRepository.findByName(authorName);
    }

    public String addAuthor(String authorName, String birthDate) {
        if (authorRepository.existsByName(authorName)) {
            return "This authors already exists in the database.";
        } else {
            Author newAuthor = new Author(authorName, birthDate);
            authorRepository.save(newAuthor);
            return "Author successfully added in the database.";
        }
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
