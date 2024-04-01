package expression.generic;

import expression.generic.type.CalcInt;

public class CheckedAbs extends Abs<CalcInt> {
    public CheckedAbs(GenericExpression<CalcInt> arg) {
        super(arg);
    }

    @Override
    public CalcInt doOperation(CalcInt arg) {
        if (arg.getNum() == Integer.MIN_VALUE) {
            throw createOverflowException(arg.getNum());
        }
        return arg.abs();
    }
}
