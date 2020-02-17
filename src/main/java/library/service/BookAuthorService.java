package library.service;

import java.util.Set;

public interface BookAuthorService {
    void saveAuthorsInBook(Set<Long> authorsId, Long bookId);

    void saveAuthorInBook(Long authorId, Long bookId);
}
