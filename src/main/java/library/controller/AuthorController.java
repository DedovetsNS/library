package library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import library.dto.AuthorDto;
import library.dto.groups.Add;
import library.dto.groups.Details;
import library.dto.groups.Update;
import library.model.Author;
import library.service.AuthorService;
import library.transformer.impl.AuthorTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static library.log.dictionary.ControllerMessages.*;

@Slf4j
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
        AuthorDto addedAuthorDto = authorService.add(authorDto);
        log.info(LOG_ADD_NEW, Author.class.toString(), addedAuthorDto.toString());
        return addedAuthorDto;
    }

    @JsonView(Details.class)
    @GetMapping("{id}")
    public AuthorDto getById(@PathVariable("id") Long id) {
        Author author = authorService.findById(id);
        log.info(LOG_GET, Author.class.toString(), author.toString());
        return authorTransformer.toDto(author);
    }

    @JsonView(Details.class)
    @GetMapping("name/{name}")
    public AuthorDto getByName(@PathVariable("name") String name) {
        Author author = authorService.findByName(name);
        log.info(LOG_GET, Author.class.toString(), author.toString());
        return authorTransformer.toDto(author);
    }

    @JsonView(Details.class)
    @GetMapping
    public Set<AuthorDto> findAll() {
        Set<Author> authors = authorService.findAll();
        log.info(LOG_GET_ALL, Author.class.toString());
        return authorTransformer.toDto(authors);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        authorService.deleteById(id);
        log.info(LOG_DELETE_BY_ID, Author.class.toString(), id);
    }

    @JsonView(Details.class)
    @PutMapping
    public AuthorDto updateById(@RequestBody @Validated({Update.class}) AuthorDto authorDto) {
        AuthorDto updatableAuthorDto = authorService.update(authorDto);
        log.info(LOG_UPDATE, Author.class.toString(), updatableAuthorDto.toString());
        return updatableAuthorDto;
    }
}
