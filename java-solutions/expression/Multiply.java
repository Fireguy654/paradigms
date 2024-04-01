package expression;

public class Multiply extends BinaryOperation {
    public Multiply(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public String getSign() {
        return "*";
    }

    @Override
    public boolean isAssociative() {
        return true;
    }

    @Override
    public Priority getLeftPrior() {
        return Priority.MUL;
    }

    @Override
    public Priority getRightPrior() {
        return Priority.MUL;
    }

    @Override
    public int doOperation(int fir, int sec) {
        return fir * sec;
    }
}
