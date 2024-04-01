package expression;

public class Reverse extends UnaryOperation {
    public Reverse(PriorExpression arg) {
        super(arg);
    }

    @Override
    public String getSign() {
        return "reverse";
    }

    @Override
    public int doOperation(int argument) {
        int ret = 0;
        while (argument != 0) {
            ret = ret * 10 + argument % 10;
            argument /= 10;
        }
        return ret;
    }
}
