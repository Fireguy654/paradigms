package expression.exceptions;

import expression.Lcm;
import expression.PriorExpression;

import static expression.MathFuncs.getGCD;
import static expression.MathFuncs.isMultOverflowed;

public class CheckedLcm extends Lcm {
    public CheckedLcm(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int doOperation(int fir, int sec) {
        if (fir == 0 && sec == 0) {
            return 0;
        }
        int gcd = getGCD(fir, sec);
        if (isMultOverflowed(sec / gcd, fir)) {
            throw createOverflowException(fir, sec);
        }
        return sec / getGCD(fir, sec) * fir;
    }
}
