package expression.generic.type;

import expression.exceptions.TypeParseException;
import expression.exceptions.UnsupportedOperationException;

public interface Calculable <T> {
    default T parse(String num) {
        throw new TypeParseException("Parsing is undefined for type: " + this.getClass());
    }

    default T valueOf(int num) {
        throw new TypeParseException("Converting from int to '" + this.getClass() + "' type is unsupported");
    }

    Object getNum();

    @Override
    String toString();

    default T add(T oth) {
        throw new UnsupportedOperationException("Operation add is unsupported for this type: " + this.getClass());
    }

    default T sub(T oth) {
        throw new UnsupportedOperationException("Operation subtract is unsupported for this type: " + this.getClass());
    }

    default T mult(T oth) {
        throw new UnsupportedOperationException("Operation multiply is unsupported for this type: " + this.getClass());
    }

    default T div(T oth) {
        throw new UnsupportedOperationException("Operation divide is unsupported for this type: " + this.getClass());
    }

    default T mod(T oth) {
        throw new UnsupportedOperationException("Operation module is unsupported for this type: " + this.getClass());
    }

    default T square() {
        throw new UnsupportedOperationException("Operation square is unsupported for this type: " + this.getClass());
    }

    default T abs() {
        throw new UnsupportedOperationException("Operation abs is unsupported for this type: " + this.getClass());
    }

    default T neg() {
        throw new UnsupportedOperationException("Operation negate is unsupported for this type: " + this.getClass());
    }
}
