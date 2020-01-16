package library.library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.library.dto.BookDto;
import library.library.dto.groups.Add;
import library.library.dto.groups.Details;
import library.library.model.Book;
import library.library.service.BookService;
import library.library.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private Transformer transformer;

    @JsonView(Details.class)
    @PostMapping("/add")
    public BookDto add(@RequestBody @Validated({Add.class}) BookDto bookDto) {
        Book addBook = transformer.fromBookDtoToBook(bookDto);
        addBook = bookService.addBook(addBook);
        return transformer.fromBookToBookDto(addBook);
    }

    @JsonView(Details.class)
    @GetMapping("/findAll")
    public List<BookDto> findAll() {
        List<Book> allBooks = bookService.findAll();
        List<BookDto> allBooksDto = transformer.fromBookToBookDto(allBooks);
        return allBooksDto;
    }
}
