package expression.parser;

import java.util.function.IntPredicate;

public abstract class BaseParser {
    public static final char END = 0;
    private CharSource source;
    private char cur;

    protected BaseParser() {
        setSource(new StringCharSource(""));
    }

    protected BaseParser(CharSource source) {
        setSource(source);
    }

    protected void setSource(CharSource newSource) {
        source = newSource;
        take();
    }

    protected boolean test(char expected) {
        return cur == expected;
    }

    protected boolean testLetter() {
        return Character.isLetter(cur);
    }

    protected boolean testDigit() {
        return Character.isDigit(cur);
    }

    protected boolean between(char min, char max) {
        return min <= cur && cur <= max;
    }

    protected char getCur() {
        return cur;
    }

    protected char take() {
        final char res = cur;
        cur = source.hasNext() ? source.next() : END;
        return res;
    }

    protected boolean take(char expected) {
        if (test(expected)) {
            take();
            return true;
        } else {
            return false;
        }
    }

    protected void skipWhitespaces() {
        while (Character.isWhitespace(cur)) {
            take();
        }
    }

    protected String getSpecToken(IntPredicate isPartOfToken) {
        StringBuilder token = new StringBuilder();
        while (isPartOfToken.test(cur)) {
            token.append(take());
        }
        return token.toString();
    }

    protected String getLetterOrDigitToken() {
        return getSpecToken(cp -> Character.isLetter(cp) || Character.isDigit(cp));
    }

    protected String errorChar() {
        return cur == END ? "END" : "'" + cur + "'";
    }

    protected IllegalArgumentException error(String message) { // :NOTE: модификатор доступа
        return source.error(message);
    }
}
