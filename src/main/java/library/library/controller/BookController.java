package library.library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.library.dto.BookDto;
import library.library.dto.groups.Add;
import library.library.dto.groups.Details;
import library.library.dto.groups.Update;
import library.library.model.Book;
import library.library.service.impl.BookServiceImpl;
import library.library.service.impl.EmailServiceImpl;
import library.library.transformer.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {
    private final BookServiceImpl bookService;
    private final BookTransformer bookTransformer;

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    public BookController(BookServiceImpl bookService, BookTransformer bookTransformer) {
        this.bookService = bookService;
        this.bookTransformer = bookTransformer;
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
        List<BookDto> allBooksDto = bookTransformer.toBookDto(allBooks);
        return allBooksDto;
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public BookDto getById(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);
        BookDto bookDto = bookTransformer.toBookDto(book);
        return bookDto;
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
