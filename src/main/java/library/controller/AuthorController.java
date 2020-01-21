package library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.AuthorDto;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.dto.groups.Update;
import library.model.Author;
import library.service.impl.AuthorServiceImpl;
import library.transformer.AuthorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorServiceImpl authorService;
    private final AuthorTransformer authorTransformer;

    @Autowired
    public AuthorController(AuthorServiceImpl authorService, AuthorTransformer authorTransformer) {
        this.authorService = authorService;
        this.authorTransformer = authorTransformer;
    }

    @JsonView(Details.class)
    @PostMapping
    public AuthorDto add(@RequestBody @Validated({Add.class}) AuthorDto authorDto) {
        Author addAuthor = authorTransformer.toAuthor(authorDto);
        addAuthor = authorService.add(addAuthor);
        return authorTransformer.toAuthorDto(addAuthor);
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public AuthorDto getById(@PathVariable("id") Long id) {
        Author author = authorService.findById(id);
        AuthorDto authorDto = authorTransformer.toAuthorDto(author);
        return authorDto;
    }

    @JsonView(Details.class)
    @GetMapping("name/{name}")
    public AuthorDto getByName(@PathVariable("name") String name) {
        Author author = authorService.findByName(name);
        AuthorDto authorDto = authorTransformer.toAuthorDto(author);
        return authorDto;
    }

    @JsonView(Details.class)
    @GetMapping
    public List<AuthorDto> findAll() {
        List<Author> authors = authorService.findAll();
        List<AuthorDto> authorsDto = authorTransformer.toAuthorDto(authors);
        return authorsDto;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        authorService.deleteById(id);
    }

    @JsonView(Details.class)
    @PutMapping
    public AuthorDto updateById(@RequestBody @Validated({Update.class}) AuthorDto authorDto) {
        Author author = authorTransformer.toAuthor(authorDto);
        author = authorService.update(author);
        return authorTransformer.toAuthorDto(author);
    }
}

// TODO: 20.01.2020 SWAAAAAGGER сделать