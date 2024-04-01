package expression.exceptions;

import expression.MathFuncs;
import expression.PriorExpression;
import expression.Reverse;

public class CheckedReverse extends Reverse {
    public CheckedReverse(PriorExpression arg) {
        super(arg);
    }

    @Override
    public int doOperation(int begArg) {
        int arg = begArg;
        int ret = 0;
        while (arg != 0) {
            if (arg < 10 && arg > -10 &&
                (MathFuncs.isMultOverflowed(ret, 10) || MathFuncs.isSumOverflowed(ret * 10, arg % 10))) {
                throw createOverflowException(begArg);
            }
            ret = ret * 10 + arg % 10;
            arg /= 10;
        }
        return ret;
    }
}
