package expression.generic;

import expression.MathFuncs;
import expression.generic.type.CalcInt;

public class CheckedMultiply extends Multiply<CalcInt> {
    public CheckedMultiply(GenericExpression<CalcInt> arg1, GenericExpression<CalcInt> arg2) {
        super(arg1, arg2);
    }

    @Override
    public CalcInt doOperation(CalcInt fir, CalcInt sec) {
        if (MathFuncs.isMultOverflowed(fir.getNum(), sec.getNum())) {
            throw createOverflowException(fir.getNum(), sec.getNum());
        }
        return fir.mult(sec);
    }
}
