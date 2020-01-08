package library.library.controller;

import library.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @GetMapping("/add")
    public String add(@RequestParam Map<String,String> addParams){
        String authorName = addParams.get("authorName");
        String birthDate = addParams.get("birthday");
// TODO: 08.01.2020 проверка на notNull входных параметров 
        return authorService.addAuthor(authorName,birthDate);
    }
}
