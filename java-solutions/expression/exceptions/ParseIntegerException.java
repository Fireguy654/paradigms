package expression.exceptions;

public class ParseIntegerException extends ParseException {
    public ParseIntegerException(String message) {
        super(message);
    }

    public ParseIntegerException(String message, Throwable cause) {
        super(message, cause);
    }
}
