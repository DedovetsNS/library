package library.service;

import library.model.Author;
import library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByName(String bookName);

    Book add(Book book);

    Book findById(Long id);

    void deleteById(Long id);

    boolean existByName(String name);

    Integer getQuantityInStockByName(String name);

    Book update(Book book);

    void takeBookToLoan(String bookName, Integer quantity);

    void returnBook(String bookName, Integer quantity);

    void removeAuthor(Book book, Author deletedAuthor);
}
