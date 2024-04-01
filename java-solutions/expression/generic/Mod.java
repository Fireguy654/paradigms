package expression.generic;

import expression.Priority;
import expression.generic.type.Calculable;

public class Mod <T extends Calculable<T>> extends BinaryOperation<T> {
    public Mod(GenericExpression<T> arg1, GenericExpression<T> arg2) {
        super(arg1, arg2);
    }

    @Override
    public String getSign() {
        return "mod";
    }

    @Override
    public boolean isAssociative() {
        return false;
    }

    @Override
    public T doOperation(T fir, T sec) {
        return fir.mod(sec);
    }

    @Override
    public Priority getLeftPrior() {
        return Priority.MUL;
    }

    @Override
    public Priority getRightPrior() {
        return Priority.MUL;
    }
}
