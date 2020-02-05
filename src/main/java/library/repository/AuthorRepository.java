package library.repository;

import library.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Set<Author> findAll();

    boolean existsByName(String authorName);

    Optional<Author> findByName(String authorName);

    @Query(nativeQuery = true, value = "SELECT *" +
            "FROM author where author.id in (select author.id from book_author where book_id=:book_id);")
    Set<Author> findAuthorsOfBook(@Param("book_id") Long bookId);

}


