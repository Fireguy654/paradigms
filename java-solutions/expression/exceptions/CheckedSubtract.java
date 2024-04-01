package expression.exceptions;

import expression.*;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int doOperation(int fir, int sec) {
        if (MathFuncs.isSubOverflowed(fir, sec)) {
            throw createOverflowException(fir, sec);
        }
        return fir - sec;
    }
}
