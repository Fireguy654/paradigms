package expression.exceptions;

import expression.Gcd;
import expression.PriorExpression;

import static expression.MathFuncs.getGCD;

public class CheckedGcd extends Gcd {
    public CheckedGcd(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public int doOperation(int fir, int sec) {
        return getGCD(fir, sec);
    }
}
