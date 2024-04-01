package expression.exceptions;

import expression.Divide;
import expression.MathFuncs;
import expression.PriorExpression;

public class CheckedDivide extends Divide {
    public CheckedDivide(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int doOperation(int fir, int sec) {
        if (sec == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        if (MathFuncs.isDivOverflowed(fir, sec)) {
            throw createOverflowException(fir, sec);
        }
        return fir / sec;
    }
}
