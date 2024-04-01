package expression.generic;

import expression.exceptions.DivisionByZeroException;
import expression.generic.type.CalcInt;

public class CheckedMod extends Mod<CalcInt> {
    public CheckedMod(GenericExpression<CalcInt> arg1, GenericExpression<CalcInt> arg2) {
        super(arg1, arg2);
    }

    @Override
    public CalcInt doOperation(CalcInt fir, CalcInt sec) {
        if (sec.getNum() == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        return fir.mod(sec);
    }
}
