package library.service.impl;

import library.exception.AlreadyExistByNameException;
import library.exception.EmptyDataBaseException;
import library.exception.NotFoundByIdException;
import library.exception.NotFoundException;
import library.model.Author;
import library.model.Book;
import library.repository.AuthorRepository;
import library.service.AuthorService;
import library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private BookService bookService;

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

    @Transactional
    @Override
    public Author add(Author author) {
        if (authorRepository.existsByName(author.getName())) {
            throw new AlreadyExistByNameException("Author", author.getName());
        } else {
            return authorRepository.save(author);
        }
    }

    @Transactional
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

    @Transactional
    @Override
    public void deleteById(Long id) {
        Author deletedAuthor = findById(id);
        Collection<Book> books = deletedAuthor.getBooks();
        for (Book book : books) {
            bookService.removeAuthor(book, deletedAuthor);
        }
        authorRepository.deleteById(id);
    }

    @Transactional
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
