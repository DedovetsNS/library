package library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmptyDataBaseException extends ResponseStatusException {
    public EmptyDataBaseException(String nameDB) {
        super(HttpStatus.NOT_FOUND, "Base of " + nameDB + " is empty.");
    }
}
