package library.repository;

import library.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Set<Book> findAll();

    boolean existsByName(String name);

    Optional<Book> findByName(String bookName);

    @Query(nativeQuery = true, value = "SELECT * FROM book where book.id in (select book_id from book_author where author_id=:author_id);")
    Set<Book> findBooksInAuthor(@Param("author_id") Long authorId);


}
