package library.exception;

public class NotAllAuthorsDataException extends RuntimeException {
    public NotAllAuthorsDataException() {
        super("Must specify all fields of the author or indicate the name of an existing one in the database.");
    }
}
