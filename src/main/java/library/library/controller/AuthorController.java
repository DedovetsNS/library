package library.library.controller;

import library.library.dto.AuthorDto;
import library.library.dto.groups.Add;
import library.library.model.Author;
import library.library.service.AuthorService;
import library.library.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @Autowired
    Transformer transformer;

    @PostMapping("/add")
    public AuthorDto add(@RequestBody @Validated({Add.class}) AuthorDto authorDto) {
        Author addAuthor = transformer.fromAuthorDtoToAuthor(authorDto);
        authorService.addAuthor(addAuthor);
        return transformer.fromAuthorToAuthorDto(addAuthor);
    }

    @GetMapping("/findAll")
    public List<AuthorDto> findAll() {
        List<Author> authors = authorService.findAll();
        List<AuthorDto> authorsDto = transformer.fromAuthorListToAuthorDtoList(authors);
        return authorsDto;
    }
}