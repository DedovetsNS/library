package library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.BookDto;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.dto.groups.Update;
import library.model.Book;
import library.service.BookService;
import library.service.impl.BookServiceImpl;
import library.service.impl.EmailServiceImpl;
import library.transformer.impl.BookTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static library.log.dictionary.ControllerMessages.*;

@Slf4j
@RestController
@RequestMapping(path = "/book")
public class BookController {
    private final BookService bookService;
    private final BookTransformer bookTransformer;

    @Autowired
    public BookController(BookServiceImpl bookService, BookTransformer bookTransformer, EmailServiceImpl emailService) {
        this.bookService = bookService;
        this.bookTransformer = bookTransformer;
    }

    @JsonView(Details.class)
    @PostMapping
    public BookDto add(@RequestBody @Validated({Add.class}) BookDto bookDto) {
        BookDto addedBook = bookService.add(bookDto);
        log.info(LOG_ADD_NEW, Book.class.toString(), addedBook.toString());
        return addedBook;
    }

    @JsonView(Details.class)
    @GetMapping
    public Set<BookDto> findAll() {
        Set<Book> allBooks = bookService.findAll();
        log.info(LOG_GET_ALL, Book.class.toString());
        return bookTransformer.toDto(allBooks);
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public BookDto getById(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);
        log.info(LOG_GET, Book.class.toString(), book.toString());
        return bookTransformer.toDto(book);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        log.info(LOG_DELETE_BY_ID, Book.class.toString(), id);
    }

    @JsonView(Details.class)
    @PutMapping
    public BookDto updateById(@RequestBody @Validated({Update.class}) BookDto bookDto) {
        BookDto updatableBookDto = bookService.update(bookDto);
        log.info(LOG_UPDATE, Book.class.toString(), updatableBookDto.toString());
        return updatableBookDto;
    }
}
