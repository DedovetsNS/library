package library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Must specify all fields of the author or indicate the name of an existing one in the database.")
public class NotAllAuthorsDataException extends RuntimeException {
}
