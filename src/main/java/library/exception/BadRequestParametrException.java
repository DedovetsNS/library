package library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class BadRequestParametrException extends ResponseStatusException {
    public BadRequestParametrException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}