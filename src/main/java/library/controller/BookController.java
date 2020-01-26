package library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.BookDto;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.dto.groups.Update;
import library.model.Book;
import library.service.impl.BookServiceImpl;
import library.service.impl.EmailServiceImpl;
import library.transformer.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {
    private final BookServiceImpl bookService;
    private final BookTransformer bookTransformer;
    private final EmailServiceImpl emailService;

    @Autowired
    public BookController(BookServiceImpl bookService, BookTransformer bookTransformer, EmailServiceImpl emailService) {
        this.bookService = bookService;
        this.bookTransformer = bookTransformer;
        this.emailService = emailService;
    }

    @JsonView(Details.class)
    @PostMapping
    public BookDto add(@RequestBody @Validated({Add.class}) BookDto bookDto) {
        Book book = bookTransformer.toBook(bookDto);
        book = bookService.add(book);
        return bookTransformer.toBookDto(book);
    }

    @JsonView(Details.class)
    @GetMapping
    public List<BookDto> findAll() {
        List<Book> allBooks = bookService.findAll();
        return bookTransformer.toBookDto(allBooks);
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public BookDto getById(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);
        return bookTransformer.toBookDto(book);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }

    @JsonView(Details.class)
    @PutMapping
    public BookDto updateById(@RequestBody @Validated({Update.class}) BookDto bookDto) {
        Book book = bookTransformer.toBook(bookDto);
        book = bookService.update(book);
        return bookTransformer.toBookDto(book);
    }
}
