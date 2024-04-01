package expression.exceptions;

import expression.PriorExpression;
import expression.UnaryOperation;

import static expression.MathFuncs.pow;

public class CheckedPow10 extends UnaryOperation {
    public CheckedPow10(PriorExpression arg) {
        super(arg);
    }

    @Override
    public String getSign() {
        return "pow10";
    }

    @Override
    public int doOperation(int arg) {
        if (arg < 0) {
            throw new WrongPowLogArgException("Pow with negative argument: '" + arg + "'");
        }
        try {
            return pow(10, arg);
        } catch (IntOverflowException ex) {
            throw createOverflowException(arg, ex);
        }
    }
}
