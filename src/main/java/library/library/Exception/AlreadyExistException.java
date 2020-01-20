package library.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistException extends ResponseStatusException {
    public AlreadyExistException(String searchObject, String param, String value) {
        super(HttpStatus.BAD_REQUEST, searchObject + " with " + param +
                " " + value + " is already exist");
    }
}