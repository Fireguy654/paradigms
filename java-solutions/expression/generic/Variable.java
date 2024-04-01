package expression.generic;

import expression.Priority;
import expression.generic.type.Calculable;

public class Variable <T extends Calculable<T>> implements GenericExpression<T> {
    private final T creator;
    private final String name;

    public Variable(String name, T creator) {
        this.name = name;
        this.creator = creator;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object oth) {
        if (oth instanceof Variable) {
            return this.name.equals(((Variable<?>) oth).getName());
        }
        return false;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        switch (this.name) {
            case "x" -> {
                return creator.valueOf(x);
            }
            case "y" -> {
                return creator.valueOf(y);
            }
            case "z" -> {
                return creator.valueOf(z);
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
