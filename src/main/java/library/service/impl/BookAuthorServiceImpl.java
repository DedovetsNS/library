package library.service.impl;

import library.model.BookAuthor;
import library.repository.BookAuthorRepository;
import library.service.BookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;

    @Autowired
    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    public void saveAuthorsInBook(Set<Long> authorsId, Long bookId) {
        for (Long authorId : authorsId) {
            BookAuthor bookAuthor = new BookAuthor();
            bookAuthor.setAuthorId(authorId);
            bookAuthor.setBookId(bookId);
            bookAuthorRepository.save(bookAuthor);
        }
    }

    @Override
    public void saveAuthorInBook(Long authorId, Long bookId) {
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setAuthorId(authorId);
        bookAuthor.setBookId(bookId);
        bookAuthorRepository.save(bookAuthor);
    }


}
