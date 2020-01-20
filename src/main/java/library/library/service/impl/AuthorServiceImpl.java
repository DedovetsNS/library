package library.library.service.impl;

import library.library.Exception.AlreadyExistByNameException;
import library.library.Exception.EmptyDataBaseException;
import library.library.Exception.NotFoundByIdException;
import library.library.Exception.NotFoundException;
import library.library.model.Author;
import library.library.model.Book;
import library.library.repository.AuthorRepository;
import library.library.service.AuthorService;
import library.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
// TODO: 20.01.2020 транзакции во все сервисы 

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private  BookService bookService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean existsByName(String authorName) {
        if (authorRepository.existsByName(authorName)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Author add(Author author) {
        if (authorRepository.existsByName(author.getName())) {
            throw new AlreadyExistByNameException("Author", author.getName());
        } else {
            return authorRepository.save(author);
        }
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            throw new EmptyDataBaseException("Authors");
        }
        return authors;
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundByIdException("Author", id));
    }

    @Override
    public void deleteById(Long id) {
        Author deletedAuthor = findById(id);
        Collection<Book> books = deletedAuthor.getBooks();
        for (Book book : books) {
            bookService.removeAuthor(book,deletedAuthor);
        }
        authorRepository.deleteById(id);
    }

    @Override
    public Author update(Author author) {
        Author updatableAuthor = findById(author.getId());
        updatableAuthor.setBirthday(author.getBirthday());
        updatableAuthor.setName(author.getName());
        return authorRepository.save(updatableAuthor);
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findByName(name).orElseThrow(() -> new NotFoundException("Author", "name", name));
    }
}
