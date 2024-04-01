package expression;

import static expression.MathFuncs.getGCD;

public class Gcd extends BinaryOperation {
    public Gcd(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public String getSign() {
        return "gcd";
    }

    @Override
    public boolean isAssociative() {
        return true;
    }

    @Override
    public int doOperation(int fir, int sec) {
        return getGCD(fir, sec);
    }

    @Override
    public Priority getLeftPrior() {
        return Priority.GCDnLCM;
    }

    @Override
    public Priority getRightPrior() {
        return Priority.GCDnLCM;
    }
}
