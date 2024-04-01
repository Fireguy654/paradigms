package expression.generic.type;

import java.util.Objects;

public class CalcInt implements Calculable<CalcInt> {
    private final int num;

    public CalcInt(int num) {
        this.num = num;
    }

    public CalcInt(String num) {
        this.num = Integer.parseInt(num);
    }

    @Override
    public CalcInt mod(CalcInt oth) {
        return new CalcInt(num % oth.num);
    }

    @Override
    public CalcInt square() {
        return new CalcInt(num * num);
    }

    @Override
    public CalcInt abs() {
        return new CalcInt(num < 0 ? num * -1 : num);
    }

    public Integer getNum() {
        return num;
    }

    @Override
    public CalcInt parse(String num) {
        return new CalcInt(num);
    }

    @Override
    public CalcInt valueOf(int num) {
        return new CalcInt(num);
    }

    @Override
    public CalcInt add(CalcInt oth) {
        return new CalcInt(num + oth.num);
    }

    @Override
    public CalcInt sub(CalcInt oth) {
        return new CalcInt(num - oth.num);
    }

    @Override
    public CalcInt mult(CalcInt oth) {
        return new CalcInt(num * oth.num);
    }

    @Override
    public CalcInt div(CalcInt oth) {
        return new CalcInt(num / oth.num);
    }

    @Override
    public CalcInt neg() {
        return new CalcInt(-num);
    }


    @Override
    public int hashCode() {
        return Objects.hash(num, this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CalcInt) {
            return num == ((CalcInt) obj).num;
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(num);
    }
}
