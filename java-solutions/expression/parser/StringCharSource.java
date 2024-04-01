package expression.parser;

public class StringCharSource implements CharSource {
    private final String src;
    private int pos = 0;

    public StringCharSource(String src) {
        this.src = src;
    }

    @Override
    public boolean hasNext() {
        return pos < src.length();
    }

    @Override
    public char next() {
        return src.charAt(pos++);
    }

    @Override
    public IllegalArgumentException error(String message) {
        return new IllegalArgumentException(pos + ": " + message);
    }
}
