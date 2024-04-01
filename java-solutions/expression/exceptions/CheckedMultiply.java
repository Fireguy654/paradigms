package expression.exceptions;

import expression.*;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int doOperation(int fir, int sec) {
        if (MathFuncs.isMultOverflowed(fir, sec)) {
            throw createOverflowException(fir, sec);
        }
        return fir * sec;
    }
}
