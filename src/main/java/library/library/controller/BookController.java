package library.library.controller;

import library.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/add")
    public String add(@RequestParam Map<String,String> addParams){
       String bookName = addParams.get("bookName");
       String publisher = addParams.get("publisher");
       String authors = addParams.get("authors");
       String totalQuantity = addParams.get("totalQuantity");

    return bookService.addNewBook(bookName,publisher,authors,totalQuantity);
    }

//    @GetMapping("createAll")
//    public String createAll(){
//        Book book1 = new Book("Old man and sea");
//        Book book2 = new Book("War and peace");
//        Book book3 = new Book("Died souls");
//        Author author1 = new Author("Pushkin","39");
//        Author author2 = new Author("Dostaevski","39");
//        Author author3 = new Author("Gogol","39");
//
//        book1.setAuthors(Arrays.asList(author1,author3));
//        book2.setAuthors(Arrays.asList(author1,author2,author3));
//        book3.setAuthors(Arrays.asList(author1));
//
//        author1.setBooks(Arrays.asList(book1,book2,book3));
//        author2.setBooks(Arrays.asList(book2));
//        author3.setBooks(Arrays.asList(book1,book2));
//
//       bookRepository.saveAll(Arrays.asList(book1,book2,book3));
//
//
//
//
//
//        return "Книги созданы  Books are created";
//    }
//
//    @GetMapping("/findAll")
//    public List<Book> findAll(){
//        List<Book> books = bookRepository.findAll();
//        for (Book books  : books  ) {
//            books.getAuthors().stream().forEach(authors -> authors.setBooks(null));
//        }
//
//        return  books;
//    }

//    @GetMapping("/findAll")
//    public List<Author> findAll(){
//        List<Author> authors  = authorRepository.findAll();
//        authors.stream().forEach(authors -> authors.setBooks(new Book(authors.getBooks())));
//        return  authors;
//    }

}
