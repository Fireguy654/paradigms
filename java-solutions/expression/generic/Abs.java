package expression.generic;

import expression.generic.type.Calculable;

public class Abs <T extends Calculable<T>> extends UnaryOperation<T> {
    public Abs(GenericExpression<T> arg) {
        super(arg);
    }

    @Override
    public String getSign() {
        return "abs";
    }

    @Override
    public T doOperation(T arg) {
        return arg.abs();
    }
}
