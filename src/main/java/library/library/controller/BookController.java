package library.library.controller;

import library.library.dto.BookDto;
import library.library.dto.groups.Add;
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
    BookService bookService;
    @Autowired
    Transformer transformer;
// TODO: 13.01.2020 автовайринг через конструктор везде

    @PostMapping("/add")
    public BookDto add(@RequestBody @Validated({Add.class}) BookDto bookDto) {
        Book addBook = transformer.fromBookDtoToBook(bookDto);
        bookService.addBook(addBook);
        return transformer.fromBookToBookDto(addBook);
    }

    // TODO: 14.01.2020 разобраться с @JsonView({Details.class}), аналогично в остальных
    @GetMapping("/findAll")
    public List<BookDto> findAll() {
        List<Book> allBooks = bookService.findAll();
        List<BookDto> allBooksDto = transformer.fromBookListToBookDtoList(allBooks);
        return allBooksDto;
    }
}
