package expression.exceptions;

public class TypeParseException extends ParseException {
    public TypeParseException(String message) {
        super(message);
    }

    public TypeParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
