package library.library.repository;

import library.library.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAll();
    boolean existsByName(String authorName);
    Author findByName(String authorName);
}
