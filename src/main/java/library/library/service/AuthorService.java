package library.library.service;

import library.library.model.Author;

import java.util.List;

public interface AuthorService {
    boolean existsByName(String authorName);

    Author add(Author author);

    List<Author> findAll();

    Author findById(Long id);

    void deleteById(Long id);

    Author update(Author author);

    Author findByName(String name);
}
