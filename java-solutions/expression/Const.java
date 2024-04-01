package expression;

public class Const implements PriorExpression {
    private final int num;

    public Const(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.num;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.num);
    }

    @Override
    public boolean equals(Object oth) {
        if (oth instanceof Const) {
            return this.num == ((Const) oth).getNum();
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(this.num);
    }

    @Override
    public String toMiniString() {
        return this.toString();
    }

    @Override
    public Priority getLeftPrior() {
        return Priority.VAL;
    }

    @Override
    public Priority getRightPrior() {
        return Priority.VAL;
    }
}
