package expression;

public class Add extends BinaryOperation {
    public Add(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public String getSign() {
        return "+";
    }

    @Override
    public boolean isAssociative() {
        return true;
    }

    @Override
    public Priority getLeftPrior() {
        return Priority.SUM;
    }

    @Override
    public Priority getRightPrior() {
        return Priority.SUM;
    }

    @Override
    public int doOperation(int fir, int sec) {
        return fir + sec;
    }
}
