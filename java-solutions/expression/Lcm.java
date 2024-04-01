package expression;

import static expression.MathFuncs.getGCD;

public class Lcm extends BinaryOperation {
    public Lcm(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public String getSign() {
        return "lcm";
    }

    @Override
    public boolean isAssociative() {
        return true;
    }

    @Override
    public int doOperation(int fir, int sec) {
        return fir == 0 && sec == 0 ? 0 : sec / getGCD(fir, sec) * fir;
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
