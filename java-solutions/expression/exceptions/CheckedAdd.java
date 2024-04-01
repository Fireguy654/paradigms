package expression.exceptions;

import expression.MathFuncs;
import expression.Add;
import expression.PriorExpression;

public class CheckedAdd extends Add {
    public CheckedAdd(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int doOperation(int fir, int sec) {
        if (MathFuncs.isSumOverflowed(fir, sec)) {
            throw createOverflowException(fir, sec);
        }
        return fir + sec;
    }
}
