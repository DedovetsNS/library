package library.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistByNameException extends ResponseStatusException {
    public AlreadyExistByNameException(String searchObject, String name) {
        super(HttpStatus.BAD_REQUEST, searchObject + " with name " + name + " is already exist");
    }
}
