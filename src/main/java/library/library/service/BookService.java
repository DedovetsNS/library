package library.library.service;

import library.library.Exception.BookAlreadyExistException;
import library.library.Exception.NotAllAuthorsDataException;
import library.library.model.Author;
import library.library.model.Book;
import library.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    AuthorService authorService;

    boolean existByName(String name) {
        return bookRepository.existsByName(name);
    }

    Integer getQuantityInStockByName(String name) {
        return bookRepository.getBookByName(name).getIn_stock_quantity();
    }

    Book getByName(String bookName) {
        return bookRepository.getBookByName(bookName);
    }

    void giveBook(String bookName, Integer quantity) {
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

    public String changeTotalQuantity(Integer changeSize, String bookName) {
        Book changeQuantityBook = findByName(bookName);
        Integer oldTotalQuantity = changeQuantityBook.getTotalQuantity();
        Integer oldInStockQuantity = changeQuantityBook.getIn_stock_quantity();

        if ((changeSize < 0 && changeSize <= oldInStockQuantity) || changeSize > 0) {
            changeQuantityBook.setTotalQuantity(oldTotalQuantity + changeSize);
            changeQuantityBook.setIn_stock_quantity(oldInStockQuantity + changeSize);
            return "Number of books changed.";
        } else return "You want to pick up more books than are available.";
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
            } else if(author.getName()==null||author.getBirthday()==null){
                throw new NotAllAuthorsDataException();
            }else {
                authorsToSave.add(author);
            }
        }
        addBook.setIn_stock_quantity(addBook.getTotalQuantity());
        addBook.setAuthors(authorsToSave);
       return bookRepository.save(addBook);
    }
}
