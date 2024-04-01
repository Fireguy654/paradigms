package expression.generic;

import expression.generic.type.Calculable;

public class Negate <T extends Calculable<T>> extends UnaryOperation<T> {
    public Negate(GenericExpression<T> arg) {
        super(arg);
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    public T doOperation(T argument) {
        return argument.neg();
    }
}
