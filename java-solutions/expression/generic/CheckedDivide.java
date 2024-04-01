package expression.generic;

import expression.MathFuncs;
import expression.exceptions.DivisionByZeroException;
import expression.generic.type.CalcInt;

public class CheckedDivide extends Divide<CalcInt> {
    public CheckedDivide(GenericExpression<CalcInt> arg1, GenericExpression<CalcInt> arg2) {
        super(arg1, arg2);
    }

    @Override
    public CalcInt doOperation(CalcInt fir, CalcInt sec) {
        if (sec.getNum() == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        if (MathFuncs.isDivOverflowed(fir.getNum(), sec.getNum())) {
            throw createOverflowException(fir.getNum(), sec.getNum());
        }
        return fir.div(sec);
    }
}
