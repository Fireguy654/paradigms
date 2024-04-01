package expression.exceptions;

import expression.Negate;
import expression.PriorExpression;

public class CheckedNegate extends Negate {
    public CheckedNegate(PriorExpression arg) {
        super(arg);
    }

    @Override
    public int doOperation(int arg) {
        if (arg == Integer.MIN_VALUE) {
            throw createOverflowException(arg);
        }
        return -arg;
    }
}
