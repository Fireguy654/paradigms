package expression.generic;

import expression.Priority;
import expression.ToMiniString;
import expression.generic.type.Calculable;

public interface GenericExpression <T extends Calculable<T>> extends ToMiniString {
    Priority getLeftPrior();
    Priority getRightPrior();
    T evaluate(int x, int y, int z);
}
