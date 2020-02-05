package library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.AuthorDto;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.dto.groups.Update;
import library.model.Author;
import library.service.AuthorService;
import library.transformer.AuthorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorTransformer authorTransformer;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorTransformer authorTransformer) {
        this.authorService = authorService;
        this.authorTransformer = authorTransformer;
    }

    @JsonView(Details.class)
    @PostMapping
    public AuthorDto add(@RequestBody @Validated({Add.class}) AuthorDto authorDto) {
        return authorService.add(authorDto);
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public AuthorDto getById(@PathVariable("id") Long id) {
        Author author = authorService.findById(id);
        return authorTransformer.toAuthorDto(author);
    }

    @JsonView(Details.class)
    @GetMapping("name/{name}")
    public AuthorDto getByName(@PathVariable("name") String name) {
        Author author = authorService.findByName(name);
        return authorTransformer.toAuthorDto(author);
    }

    @JsonView(Details.class)
    @GetMapping
    public Set<AuthorDto> findAll() {
        Set<Author> authors = authorService.findAll();
        return authorTransformer.toAuthorDto(authors);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        authorService.deleteById(id);
    }

    @JsonView(Details.class)
    @PutMapping
    public AuthorDto updateById(@RequestBody @Validated({Update.class}) AuthorDto authorDto) {
        return authorService.update(authorDto);
    }
}
