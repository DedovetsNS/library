package library.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class NotFoundByIdException extends ResponseStatusException {
    public NotFoundByIdException(String searchObject, Long id) {
        super(HttpStatus.NOT_FOUND, searchObject + " with id " + id + " doesn't found");
    }
}

