package expression;

import expression.exceptions.IntOverflowException;

import java.util.Objects;

public abstract class BinaryOperation implements PriorExpression {
    private final PriorExpression arg1;
    private final PriorExpression arg2;

    public BinaryOperation(PriorExpression arg1, PriorExpression arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public abstract String getSign();

    @Override
    public boolean equals(Object oth) {
        if (this == oth) {
            return true;
        }
        if (oth instanceof BinaryOperation othBO) {
            return this.getClass() == othBO.getClass() &&
                    arg1.equals(othBO.arg1) &&
                    arg2.equals(othBO.arg2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg1, arg2, getClass());
    }

    @Override
    public String toString() {
        return '(' + arg1.toString() + ' ' + getSign() + ' ' + arg2.toString() + ')';
    }

    public abstract boolean isAssociative();

    @Override
    public String toMiniString() {
        StringBuilder ret = new StringBuilder();
        if (arg1.getLeftPrior().compareTo(this.getLeftPrior()) < 0) {
            ret.append('(').append(arg1.toMiniString()).append(')');
        } else {
            ret.append(arg1.toMiniString());
        }
        ret.append(' ').append(this.getSign()).append(' ');
        if (arg2.getRightPrior().compareTo(this.getRightPrior()) <= 0 &&
            !(arg2.getClass() == this.getClass() && this.isAssociative())
        ) {
            ret.append('(').append(arg2.toMiniString()).append(')');
        } else {
            ret.append(arg2.toMiniString());
        }
        return ret.toString();
    }

    public abstract int doOperation(int fir, int sec);

    protected IntOverflowException createOverflowException(int fir, int sec, Throwable c) {
        return new IntOverflowException("'" + fir + " " + getSign() + " " + sec + "'" + " is not in Integer format", c);
    }

    protected IntOverflowException createOverflowException(int fir, int sec) {
        return new IntOverflowException("'" + fir + " " + getSign() + " " + sec + "'" + " is not in Integer format");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return doOperation(arg1.evaluate(x, y, z), arg2.evaluate(x, y, z));
    }
}
