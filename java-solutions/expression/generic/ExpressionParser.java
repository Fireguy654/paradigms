package expression.generic;

import expression.Priority;
import expression.exceptions.IncorrectBracketSeqException;
import expression.exceptions.InvalidBinaryOperationException;
import expression.exceptions.InvalidElementException;
import expression.generic.type.Calculable;
import expression.parser.BaseParser;
import expression.parser.StringCharSource;

import java.util.Map;

public class ExpressionParser <T extends Calculable<T> > extends BaseParser {
    private static final Map<String, Priority> PRIORS_FOR_BIN = Map.of(
            "+", Priority.SUM,
            "-", Priority.SUM,
            "*", Priority.MUL,
            "/", Priority.MUL,
            "mod", Priority.MUL,
            "gcd", Priority.GCDnLCM,
            "lcm", Priority.GCDnLCM,
            "", Priority.LOWEST
    );

    private static final String EMPTY_OPER = "";

    private String curOper = EMPTY_OPER;

    protected final T creator;

    public ExpressionParser(T creator) {
        super();
        this.creator = creator;
    }

    public GenericExpression<T> parse(String expression) {
        this.setSource(new StringCharSource(expression));
        GenericExpression<T> ret = parsePriorities(EMPTY_OPER);
        if (!test(END)) {
            throw new IncorrectBracketSeqException("There are too much closed brackets at the end of expression");
        }
        return ret;
    }

    private GenericExpression<T> parsePriorities(String prevOper) {
        GenericExpression<T> arg = parseElement();
        String foundedOper = getOper();
        while (PRIORS_FOR_BIN.get(foundedOper).compareTo(PRIORS_FOR_BIN.get(prevOper)) > 0) {
                GenericExpression<T> tmpArg = parsePriorities(foundedOper);
                switch (foundedOper) {
                    case "+" -> arg = create("add", arg, tmpArg);
                    case "-" -> arg = create("sub", arg, tmpArg);
                    case "*" -> arg = create("mult", arg, tmpArg);
                    case "/" -> arg = create("div", arg, tmpArg);
                    case "mod" -> arg = create("mod", arg, tmpArg);
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

    private GenericExpression<T> parseElement() {
        skipWhitespaces();
        GenericExpression<T> ret;
        if (take('(')) {
            ret = parsePriorities(EMPTY_OPER);
            if (!take(')')) {
                throw new IncorrectBracketSeqException("There are too few closed brackets at the end of expression");
            }
        } else if (take('-')) {
            if (between('0', '9')) {
                ret = new Const<>(parseT(new StringBuilder("-")));
            } else {
                ret = create("neg", parseElement(), null);
            }
        } else if (between('0', '9')) {
            ret = new Const<>(parseT());
        } else if (testLetter()) {
            String token = getLetterOrDigitToken();
            switch (token) {
                case "x", "y", "z" -> ret = new Variable<>(token, creator);
                case "square" -> ret = create("square", parseElement(), null);
                case "abs" -> ret = create("abs", parseElement(), null);
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

    protected GenericExpression<T> create(String oper, GenericExpression<T> fir, GenericExpression<T> sec) {
        GenericExpression<T> ret = null;
        switch (oper) {
            case "add" -> ret = new Add<>(fir, sec);
            case "sub" -> ret = new Subtract<>(fir, sec);
            case "mult" -> ret = new Multiply<>(fir, sec);
            case "div" -> ret = new Divide<>(fir, sec);
            case "mod" -> ret = new Mod<>(fir, sec);
            case "neg" -> ret = new Negate<>(fir);
            case "abs" -> ret = new Abs<>(fir);
            case "square" -> ret = new Square<>(fir);
        }
        return ret;
    }

    protected T parseT(StringBuilder numSB) {
        while (testDigit()) {
            numSB.append(take());
        }
        return creator.parse(numSB.toString());
    }

    private T parseT() {
        return parseT(new StringBuilder());
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
