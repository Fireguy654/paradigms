package expression.exceptions;

public class InvalidElementException extends ParseException {
    public InvalidElementException(String message) {
        super(message);
    }

    public InvalidElementException(String message, Throwable cause) {
        super(message, cause);
    }
}
