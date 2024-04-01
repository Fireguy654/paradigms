package expression.exceptions;

public class IntOverflowException extends CalculationException {
    public IntOverflowException(String message) {
        super(message);
    }

    public IntOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
