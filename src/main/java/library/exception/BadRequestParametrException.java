package library.exception;

public class BadRequestParametrException extends RuntimeException {
    public BadRequestParametrException(String message) {
        super(message);
    }
}