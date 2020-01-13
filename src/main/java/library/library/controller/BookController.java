package library.library.controller;

import library.library.Transformer;
import library.library.dto.*;
import library.library.model.Author;
import library.library.model.Book;
import library.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/book")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    Transformer transformer;

    @GetMapping("/add")
    public String add(@RequestParam Map<String, String> addParams) {
        String bookName = addParams.get("bookName");
        String publisher = addParams.get("publisher");
        String authors = addParams.get("authors");
        String totalQuantity = addParams.get("totalQuantity");
        return bookService.addNewBook(bookName, publisher, authors, totalQuantity);
    }


    @PostMapping("/addJSON")
    public String addJSON(@RequestBody @Validated({Add.class}) BookDto bookDto) {
        Book addBook = transformer.fromBookDtoToBook(bookDto);
        bookService.addBook(addBook);
        return "книга добавлена";
    }

    @GetMapping("/findAll")
    public List<BookResponseDto> findAll() {
        List<Book> allBooks = bookService.findAll();
        List<BookResponseDto> allBooksDto = new ArrayList<>();

        for (Book book : allBooks) {
            Collection<Author> authors = book.getAuthors();
            ArrayList<AuthorInBookDto> authorsDto = new ArrayList<>();
            for (Author author : authors) {
                authorsDto.add(new AuthorInBookDto(author.getId(), author.getName(), author.getBirthday()));
            }
            BookResponseDto bookDto = BookResponseDto.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .publisher(book.getPublisher())
                    .totalQuantity(book.getTotalQuantity())
                    .in_stock_quantity(book.getIn_stock_quantity())
                    .authors(authorsDto)
                    .build();
            allBooksDto.add(bookDto);
        }
        Collections.sort(allBooksDto);
        return allBooksDto;
    }

    @GetMapping("/changeTotalQuantity")
    public String changeQuantity(@RequestParam Integer changeSize, String bookName) {
        if (changeSize == 0) {
            return "Change size cannot be zero.";
        }
        return bookService.changeTotalQuantity(changeSize, bookName);
    }

    // TODO: 09.01.2020 добавить книгу вместе с авторами 
}
