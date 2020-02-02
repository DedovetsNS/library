package library.repository;

import library.dto.BookInAuthorDto;
import library.model.BookAuthor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Long> {

    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO book_author (book_id, author_id) VALUES (?,?)")
    BookAuthor addBookAuthor(Long bookId, Long authorId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM book_author where author_id =:author_id")
    Set<BookAuthor> findBooksInAuthor(@Param("author_id") Long authorId);

    void deleteAllByAuthorId(Long id);

    void deleteAllByBookId(Long id);

    boolean existsBookAuthorByAuthorIdAndBookId(Long authorId, Long bookId);
}

