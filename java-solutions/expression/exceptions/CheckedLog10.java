package expression.exceptions;

import expression.PriorExpression;
import expression.UnaryOperation;

public class CheckedLog10 extends UnaryOperation {
    public CheckedLog10(PriorExpression arg) {
        super(arg);
    }

    @Override
    public String getSign() {
        return "log10";
    }

    @Override
    public int doOperation(int arg) {
        if (arg < 1) {
            throw new WrongPowLogArgException("Log with non-positive argument: '" + arg + "'");
        }
        return Integer.toString(arg).length() - 1;
    }
}
