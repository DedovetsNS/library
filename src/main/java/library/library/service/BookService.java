package library.library.service;

import library.library.Exception.BookAlreadyExistException;
import library.library.Exception.NotAllAuthorsDataException;
import library.library.model.Author;
import library.library.model.Book;
import library.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private  AuthorService authorService;

    private boolean existByName(String name) {
        return bookRepository.existsByName(name);
    }

    private Integer getQuantityInStockByName(String name) {
        return bookRepository.getBookByName(name).getIn_stock_quantity();
    }

    void giveBookToLoan(String bookName, Integer quantity) {
        if (!existByName(bookName)) {
            throw new IllegalArgumentException("Book with such name does not exist.");
        }
        if (getQuantityInStockByName(bookName) < quantity) {
            throw new IllegalArgumentException("Less books in the library than you want to take.");
        }
        Book takedBook = bookRepository.getBookByName(bookName);
        Integer newQuantity = takedBook.getIn_stock_quantity() - quantity;
        takedBook.setIn_stock_quantity(newQuantity);
        bookRepository.save(takedBook);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findByName(String bookName) {
        return bookRepository.findByName(bookName);
    }

    public Book addBook(Book addBook) {
        Collection<Author> authors = addBook.getAuthors();

        if (bookRepository.existsByName(addBook.getName())) {
            throw new BookAlreadyExistException();
        }

        List<Author> authorsToSave = new ArrayList<>();
        for (Author author : authors) {
            Author existingAuthor;
            if (authorService.existsByName(author.getName())) {
                existingAuthor = authorService.getAuthorByName(author.getName());
                existingAuthor.getBooks().add(addBook);
                authorsToSave.add(existingAuthor);
            } else if (author.getName() == null || author.getBirthday() == null) {
                throw new NotAllAuthorsDataException();
            } else {
                authorsToSave.add(author);
            }
        }
        addBook.setIn_stock_quantity(addBook.getTotalQuantity());
        addBook.setAuthors(authorsToSave);
        return bookRepository.save(addBook);
    }
}
