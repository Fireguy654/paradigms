package expression.generic;

import expression.MathFuncs;
import expression.generic.type.CalcInt;

public class CheckedAdd extends Add<CalcInt> {
    public CheckedAdd(GenericExpression<CalcInt> arg1, GenericExpression<CalcInt> arg2) {
        super(arg1, arg2);
    }

    @Override
    public CalcInt doOperation(CalcInt fir, CalcInt sec) {
        if (MathFuncs.isSumOverflowed(fir.getNum(), sec.getNum())) {
            throw createOverflowException(fir.getNum(), sec.getNum());
        }
        return fir.add(sec);
    }
}
