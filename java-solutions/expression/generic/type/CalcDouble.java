package expression.generic.type;

import java.util.Objects;

public class CalcDouble implements Calculable<CalcDouble> {
    private final double num;

    public CalcDouble(double num) {
        this.num = num;
    }

    public CalcDouble(String num) {
        this.num = Double.parseDouble(num);
    }

    public Double getNum() {
        return num;
    }

    @Override
    public CalcDouble mod(CalcDouble oth) {
        return new CalcDouble(num % oth.num);
    }

    @Override
    public CalcDouble square() {
        return new CalcDouble(num * num);
    }

    @Override
    public CalcDouble abs() {
        return new CalcDouble(Math.abs(num));
    }

    @Override
    public CalcDouble parse(String num) {
        return new CalcDouble(num);
    }

    @Override
    public CalcDouble valueOf(int num) {
        return new CalcDouble(num);
    }

    @Override
    public CalcDouble add(CalcDouble oth) {
        return new CalcDouble(num + oth.num);
    }

    @Override
    public CalcDouble sub(CalcDouble oth) {
        return new CalcDouble(num - oth.num);
    }

    @Override
    public CalcDouble mult(CalcDouble oth) {
        return new CalcDouble(num * oth.num);
    }

    @Override
    public CalcDouble div(CalcDouble oth) {
        return new CalcDouble(num / oth.num);
    }

    @Override
    public CalcDouble neg() {
        return new CalcDouble(-num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CalcDouble) {
            return num == ((CalcDouble) obj).num;
        }
        return false;
    }

    @Override
    public String toString() {
        return Double.toString(num);
    }
}
