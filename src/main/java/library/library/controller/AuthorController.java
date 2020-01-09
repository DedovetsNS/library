package library.library.controller;

import library.library.dto.AuthorResponseDto;
import library.library.dto.BookInAuthorDto;
import library.library.model.Author;
import library.library.model.Book;
import library.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @GetMapping("/add")
    public String add(@RequestParam Map<String, String> addParams) {
        String authorName = addParams.get("authorName");
        String birthDate = addParams.get("birthday");
// TODO: 08.01.2020 проверка на notNull входных параметров 
        return authorService.addAuthor(authorName, birthDate);
    }

    @GetMapping("/findAll")
    public List<AuthorResponseDto> findAll() {
        List<Author> authors = authorService.findAll();
        List<AuthorResponseDto> authorsDto = new ArrayList<>();

        for (Author author : authors) {
            Collection<Book> books = author.getBooks();
            List<BookInAuthorDto> booksDto = new ArrayList<>();
            for (Book book : books) {
                booksDto.add(new BookInAuthorDto(book.getId(), book.getName(), book.getPublisher()));
            }
            authorsDto.add(new AuthorResponseDto(author.getId(), author.getName(), author.getBirthday(), booksDto));
        }
        return authorsDto;
    }
}
