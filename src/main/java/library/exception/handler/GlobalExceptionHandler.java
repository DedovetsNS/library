package library.exception.handler;

import library.exception.AlreadyExistException;
import library.exception.BadRequestParametrException;
import library.exception.NotAllAuthorsDataException;
import library.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.NOT_FOUND.value());
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({BadRequestParametrException.class, AlreadyExistException.class, NotAllAuthorsDataException.class})
    protected ResponseEntity<Object> handleBadRequestException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}