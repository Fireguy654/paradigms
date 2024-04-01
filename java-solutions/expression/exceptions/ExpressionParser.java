package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringCharSource;

import java.util.Map;

public class ExpressionParser extends BaseParser implements TripleParser {
    private static final Map<String, Priority> PRIORS_FOR_BIN = Map.of(
            "+", Priority.SUM,
            "-", Priority.SUM,
            "*", Priority.MUL,
            "/", Priority.MUL,
            "gcd", Priority.GCDnLCM,
            "lcm", Priority.GCDnLCM,
            "", Priority.LOWEST
    );

    private static final String EMPTY_OPER = "";

    private String curOper = EMPTY_OPER;

    public ExpressionParser() {
        super();
    }

    @Override
    public TripleExpression parse(String expression) {
        this.setSource(new StringCharSource(expression));
        PriorExpression ret = parsePriorities(EMPTY_OPER);
        if (!test(END)) {
            throw new IncorrectBracketSeqException("There are too much closed brackets at the end of expression");
        }
        return ret;
    }

    private PriorExpression parsePriorities(String prevOper) {
        PriorExpression arg = parseElement();
        String foundedOper = getOper();
        while (PRIORS_FOR_BIN.get(foundedOper).compareTo(PRIORS_FOR_BIN.get(prevOper)) > 0) {
                PriorExpression tmpArg = parsePriorities(foundedOper);
                switch (foundedOper) {
                    case "+" -> arg = new CheckedAdd(arg, tmpArg);
                    case "-" -> arg = new CheckedSubtract(arg, tmpArg);
                    case "*" -> arg = new CheckedMultiply(arg, tmpArg);
                    case "/" -> arg = new CheckedDivide(arg, tmpArg);
                    case "gcd" -> arg = new CheckedGcd(arg, tmpArg);
                    case "lcm" -> arg = new CheckedLcm(arg, tmpArg);
                }
                foundedOper = curOper;
        }
        return arg;
    }

    private String getOper() {
        skipWhitespaces();
        if (this.getCur() == END || test(')')) {
            curOper = EMPTY_OPER;
        } else if (testSign()) {
            curOper = Character.toString(take());
        } else if (testLetter()) {
            curOper = getLetterOrDigitToken();
            expectCorrectBinOper();
        } else {
            throw new InvalidBinaryOperationException("Unsupported binary operation beginning: " + errorChar());
        }
        skipWhitespaces();
        return curOper;
    }

    private PriorExpression parseElement() {
        skipWhitespaces();
        PriorExpression ret;
        if (take('(')) {
            ret = parsePriorities(EMPTY_OPER);
            if (!take(')')) {
                throw new IncorrectBracketSeqException("There are too few closed brackets at the end of expression");
            }
        } else if (take('-')) {
            if (between('0', '9')) {
                ret = new Const(parseInteger(new StringBuilder("-")));
            } else {
                ret = new CheckedNegate(parseElement());
            }
        } else if (between('0', '9')) {
            ret = new Const(parseInteger());
        } else if (testLetter()) {
            String token = getLetterOrDigitToken();
            switch (token) {
                case "reverse" -> ret = new CheckedReverse(parseElement());
                case "pow10" -> ret = new CheckedPow10(parseElement());
                case "log10" -> ret = new CheckedLog10(parseElement());
                case "x", "y", "z" -> ret = new Variable(token);
                default -> {
                    throw new InvalidBinaryOperationException("Unsupported operation or variable: '" + token + "'");
                }
            }
        } else {
            throw new InvalidElementException("Unsupported element beginning: " + errorChar());
        }
        skipWhitespaces();
        return ret;
    }

    private int parseInteger(StringBuilder numSB) {
        while (testDigit()) {
            numSB.append(take());
        }
        try {
            return Integer.parseInt(numSB.toString());
        } catch (NumberFormatException ex) {
            throw new ParseIntegerException("Expected number of integer type, found: '" + numSB.toString() + "'", ex);
        }
    }

    private int parseInteger() {
        return parseInteger(new StringBuilder());
    }

    private void expectCorrectBinOper(String oper) {
        if (!testBinOper(oper)) {
            throw new InvalidBinaryOperationException("Unsupported binary operation: '" + oper + "'");
        }
    }

    private void expectCorrectBinOper() {
        expectCorrectBinOper(curOper);
    }

    private boolean testBinOper(String oper) {
        return PRIORS_FOR_BIN.containsKey(oper);
    }

    private boolean testSign() {
        return test('/') || test('*') || test('+') || test('-');
    }
}
