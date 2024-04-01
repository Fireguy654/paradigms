package expression.generic;

import expression.Priority;
import expression.generic.type.Calculable;

public class Const <T extends Calculable<T>> implements GenericExpression<T> {
    private final T num;

    public Const(T num) {
        this.num = num;
    }

    public T getNum() {
        return num;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return this.num;
    }

    @Override
    public int hashCode() {
        return num.hashCode();
    }

    @Override
    public boolean equals(Object oth) {
        if (oth instanceof Const) {
            return this.num.equals(((Const <?>) oth).getNum());
        }
        return false;
    }

    @Override
    public String toString() {
        return num.toString();
    }

    @Override
    public String toMiniString() {
        return this.toString();
    }

    @Override
    public Priority getLeftPrior() {
        return Priority.VAL;
    }

    @Override
    public Priority getRightPrior() {
        return Priority.VAL;
    }
}
