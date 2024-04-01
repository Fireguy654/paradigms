package expression.exceptions;

public class IncorrectBracketSeqException extends ParseException {
    public IncorrectBracketSeqException(String message) {
        super(message);
    }

    public IncorrectBracketSeqException(String message, Throwable cause) {
        super(message, cause);
    }
}
