package expression.generic;

import expression.Priority;
import expression.exceptions.IntOverflowException;
import expression.generic.type.Calculable;

import java.util.Objects;

public abstract class UnaryOperation <T extends Calculable<T>> implements GenericExpression<T> {
    protected final GenericExpression<T> arg;

    public UnaryOperation(GenericExpression<T> arg) {
        this.arg = arg;
    }

    @Override
    public Priority getLeftPrior() {
        return Priority.UNARY;
    }

    @Override
    public Priority getRightPrior() {
        return Priority.UNARY;
    }

    public abstract String getSign();

    @Override
    public String toString() {
        return this.getSign() + "(" + arg.toString() + ')';
    }

    @Override
    public String toMiniString() {
        if (arg.getRightPrior().compareTo(this.getRightPrior()) < 0) {
            return this.getSign() + "(" + arg.toMiniString() + ')';
        } else {
            return this.getSign() + " " + arg.toMiniString();
        }
    }

    @Override
    public boolean equals(Object oth) {
        if (this == oth) {
            return true;
        }
        if (oth instanceof UnaryOperation) {
            return this.getClass() == oth.getClass() && arg.equals(((UnaryOperation <?>) oth).arg);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.arg, this.getClass());
    }

    public abstract T doOperation(T arg);

    protected IntOverflowException createOverflowException(int arg) {
        return new IntOverflowException("'" + getSign() + " " + arg + "'" + " is not in Integer format");
    }

    protected IntOverflowException createOverflowException(int arg, Throwable c) {
        return new IntOverflowException("'" + getSign() + " " + arg + "'" + " is not in Integer format", c);
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return doOperation(arg.evaluate(x, y, z));
    }
}
