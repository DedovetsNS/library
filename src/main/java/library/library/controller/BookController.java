package library.library.controller;

import library.library.dto.AuthorInBookDto;
import library.library.dto.BookResponseDto;
import library.library.model.Author;
import library.library.model.Book;
import library.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(path = "/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/add")
    public String add(@RequestParam Map<String, String> addParams) {
        String bookName = addParams.get("bookName");
        String publisher = addParams.get("publisher");
        String authors = addParams.get("authors");
        String totalQuantity = addParams.get("totalQuantity");

        return bookService.addNewBook(bookName, publisher, authors, totalQuantity);
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
