package expression.generic;

import expression.MathFuncs;
import expression.generic.type.CalcInt;

public class CheckedSubtract extends Subtract<CalcInt> {
    public CheckedSubtract(GenericExpression<CalcInt> arg1, GenericExpression<CalcInt> arg2) {
        super(arg1, arg2);
    }

    @Override
    public CalcInt doOperation(CalcInt fir, CalcInt sec) {
        if (MathFuncs.isSubOverflowed(fir.getNum(), sec.getNum())) {
            throw createOverflowException(fir.getNum(), sec.getNum());
        }
        return fir.sub(sec);
    }
}
