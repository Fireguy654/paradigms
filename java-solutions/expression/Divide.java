package expression;

public class Divide extends BinaryOperation {
    public Divide(PriorExpression arg1, PriorExpression arg2) {
        super(arg1, arg2);
    }

    @Override
    public String getSign() {
        return "/";
    }

    @Override
    public boolean isAssociative() {
        return false;
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
        return fir / sec;
    }
}
