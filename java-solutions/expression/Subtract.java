package expression;

public class Subtract extends BinaryOperation {
    public Subtract(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    public boolean isAssociative() {
        return false;
    }

    @Override
    public Priority getLeftPrior() {
        return Priority.SUM;
    }

    @Override
    public Priority getRightPrior() {
        return Priority.SUB;
    }

    @Override
    public int doOperation(int fir, int sec) {
        return fir - sec;
    }
}
