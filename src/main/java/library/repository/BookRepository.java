package library.repository;

import library.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();

    boolean existsByName(String name);

    Book getBookByName(String name);

    Book findByName(String bookName);
}
