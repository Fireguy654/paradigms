package expression.generic;

import expression.Priority;
import expression.generic.type.Calculable;

public class Add <T extends Calculable<T>> extends BinaryOperation<T> {
    public Add(GenericExpression<T> arg1, GenericExpression<T> arg2) {
        super(arg1, arg2);
    }

    @Override
    public String getSign() {
        return "+";
    }

    @Override
    public boolean isAssociative() {
        return true;
    }

    @Override
    public Priority getLeftPrior() {
        return Priority.SUM;
    }

    @Override
    public Priority getRightPrior() {
        return Priority.SUM;
    }

    @Override
    public T doOperation(T fir, T sec) {
        return fir.add(sec);
    }
}
