package expression.exceptions;

public class InvalidBinaryOperationException extends ParseException {
    public InvalidBinaryOperationException(String message) {
        super(message);
    }

    public InvalidBinaryOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
