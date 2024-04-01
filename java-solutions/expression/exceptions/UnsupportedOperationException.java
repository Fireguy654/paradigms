package expression.exceptions;

import expression.exceptions.ExpressionException;

public class UnsupportedOperationException extends ExpressionException {
    public UnsupportedOperationException(String message) {
        super(message);
    }

    public UnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
