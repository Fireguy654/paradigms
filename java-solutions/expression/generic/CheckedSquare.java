package expression.generic;

import expression.MathFuncs;
import expression.generic.type.CalcInt;

public class CheckedSquare extends Square<CalcInt> {
    public CheckedSquare(GenericExpression<CalcInt> arg) {
        super(arg);
    }

    @Override
    public CalcInt doOperation(CalcInt arg) {
        if (MathFuncs.isMultOverflowed(arg.getNum(), arg.getNum())) {
            throw createOverflowException(arg.getNum());
        }
        return arg.square();
    }
}
