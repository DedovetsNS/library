package library.service;

import library.dto.AuthorDto;
import library.model.Author;

import java.util.Set;

public interface AuthorService {
    boolean existsByName(String authorName);

    AuthorDto add(AuthorDto authorDto);

    Set<Author> findAll();

    Author findById(Long id);

    void deleteById(Long id);

    AuthorDto update(AuthorDto authorDto);

    Author findByName(String name);
}
