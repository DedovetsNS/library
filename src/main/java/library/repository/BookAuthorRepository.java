package library.repository;

import library.model.BookAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Long> {

    void deleteAllByAuthorId(Long id);

    void deleteAllByBookId(Long id);

}

