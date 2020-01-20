package library.library.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(String searchObject, String param, String value) {
        super(HttpStatus.NOT_FOUND, searchObject + " with " + param +
                " " + value + " doesn't exist");
    }
}