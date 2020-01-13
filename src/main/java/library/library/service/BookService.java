package library.library.service;

import library.library.Exception.BookAlreadyExistException;
import library.library.model.Author;
import library.library.model.Book;
import library.library.repository.AuthorRepository;
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


    public String addNewBook(String bookName, String publisher, String authors, String totalQuantity) {

        List<String> authorsNames = Arrays.asList(authors.split(";"));
        Integer totalQuantityAsInt = Integer.valueOf(totalQuantity);

        if (bookRepository.existsByName(bookName)) {
            return "This books already exists.";
        }
        ;
        Book newBook = new Book(bookName, publisher, totalQuantityAsInt);
        newBook.setIn_stock_quantity(totalQuantityAsInt);
        ArrayList<Author> authorsList = new ArrayList<>();

        // TODO: 06.01.2020 по возможности исправить на автоматиеское добавление автора в базу,
        //  если его там еще нет
        for (String authorName : authorsNames) {
            if (!authorService.existsByName(authorName)) {
                return "One of the authors is not yet in the database. First add all authors to the database.";
            } else {
                authorsList.add(authorService.getAuthorByName(authorName));
            }
        }
        newBook.setAuthors(authorsList);
        authorsList.stream().forEach(author -> author.getBooks().add(newBook));
        bookRepository.save(newBook);
        return "Book successfully added.";
    }

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

    public void addBook(Book addBook) {

        Collection<Author> authors = addBook.getAuthors();

        if (bookRepository.existsByName(addBook.getName())) {
            throw new BookAlreadyExistException();
        }

        for (Author author : authors) {
            if (authorService.existsByName(author.getName())) {
                author = authorService.getAuthorByName(author.getName());
            }
            author.getBooks().add(addBook);
        }

        addBook.setAuthors(authors);
        bookRepository.save(addBook);
    }
}
