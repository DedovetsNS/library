package library.library;

import library.library.repository.AuthorRepository;
import library.library.repository.BookRepository;
import library.library.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {
//	@Autowired
//	CustomerRepository repository;
//	@Autowired
//	BookRepository bookRepository;
//	@Autowired
//	AuthorRepository authorRepository;
	public static void main(String[] args) {SpringApplication.run(LibraryApplication.class, args); }

}
