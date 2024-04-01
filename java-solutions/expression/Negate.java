package expression;

public class Negate extends UnaryOperation {
    public Negate(PriorExpression arg) {
        super(arg);
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    public int doOperation(int argument) {
        return -argument;
    }
}
