package library.library.repository;

import library.library.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();

    boolean existsByName(String name);

    Book getBookByName(String name); // TODO: 08.01.2020 зименить метод, чтобы он сразу возвращал количество inStock по названию

    Book findByName(String bookName);
}
