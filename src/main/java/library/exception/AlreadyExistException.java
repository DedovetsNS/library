package library.exception;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String searchObject, String param, String value) {
        super(searchObject + " with " + param +
                " " + value + " is already exist");
    }
}