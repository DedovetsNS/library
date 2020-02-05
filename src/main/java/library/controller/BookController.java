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
import library.transformer.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
        return bookService.add(bookDto);
    }

    @JsonView(Details.class)
    @GetMapping
    public Set<BookDto> findAll() {
        Set<Book> allBooks = bookService.findAll();
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
        return bookService.update(bookDto);
    }
}
