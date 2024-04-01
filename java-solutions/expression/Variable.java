package expression;

public class Variable implements PriorExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object oth) {
        if (oth instanceof Variable) {
            return this.name.equals(((Variable) oth).getName());
        }
        return false;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (this.name) {
            case "x" -> {
                return x;
            }
            case "y" -> {
                return y;
            }
            case "z" -> {
                return z;
            }
        }
        throw new IllegalStateException("Name of variable doesn't match the allowed names");
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
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
