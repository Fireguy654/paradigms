package expression;

public interface PriorExpression extends TripleExpression {
    Priority getLeftPrior();
    Priority getRightPrior();
}
