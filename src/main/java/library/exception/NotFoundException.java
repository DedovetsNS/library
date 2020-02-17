package library.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String searchObject, String param, String value) {
        super(searchObject + " with " + param + " " + value + " doesn't exist");
    }
}