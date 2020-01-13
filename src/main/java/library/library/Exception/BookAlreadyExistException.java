package library.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Book with this name already exists.")
public class BookAlreadyExistException extends RuntimeException {
}