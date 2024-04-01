package expression.generic;

import expression.generic.type.Calculable;

public class Square<T extends Calculable<T>> extends UnaryOperation<T> {
    public Square(GenericExpression<T> arg) {
        super(arg);
    }

    @Override
    public String getSign() {
        return "square";
    }

    @Override
    public T doOperation(T arg) {
        return arg.square();
    }
}
