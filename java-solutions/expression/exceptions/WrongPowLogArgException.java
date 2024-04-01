package expression.exceptions;

public class WrongPowLogArgException extends CalculationException {
    public WrongPowLogArgException(String message) {
        super(message);
    }

    public WrongPowLogArgException(String message, Throwable cause) {
        super(message, cause);
    }
}
