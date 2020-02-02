package library.service;

import library.dto.BookDto;
import library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByName(String bookName);

    BookDto add(BookDto bookDto);

    Book findById(Long id);

    void deleteById(Long id);

    boolean existByName(String name);

    Integer getQuantityInStockByName(String name);

    BookDto update(BookDto bookDto);

    void takeBookToLoan(String bookName, Integer quantity);

    void returnBook(String bookName, Integer quantity);

}
