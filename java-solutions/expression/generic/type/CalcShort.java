package expression.generic.type;

import java.util.Objects;

public class CalcShort implements Calculable<CalcShort> {
    private final short num;

    public CalcShort(int num) {
        this.num = (short)num;
    }

    public CalcShort(String num) {
        this(Integer.parseInt(num));
    }

    @Override
    public CalcShort parse(String num) {
        return new CalcShort(num);
    }

    @Override
    public CalcShort valueOf(int num) {
        return new CalcShort(num);
    }

    @Override
    public Short getNum() {
        return num;
    }

    @Override
    public CalcShort add(CalcShort oth) {
        return new CalcShort(num + oth.num);
    }

    @Override
    public CalcShort sub(CalcShort oth) {
        return new CalcShort(num - oth.num);
    }

    @Override
    public CalcShort mult(CalcShort oth) {
        return new CalcShort(num * oth.num);
    }

    @Override
    public CalcShort div(CalcShort oth) {
        return new CalcShort(num / oth.num);
    }

    @Override
    public CalcShort mod(CalcShort oth) {
        return new CalcShort(num % oth.num);
    }

    @Override
    public CalcShort square() {
        return new CalcShort(num * num);
    }

    @Override
    public CalcShort abs() {
        return new CalcShort(num < 0 ? num * -1 : num);
    }

    @Override
    public CalcShort neg() {
        return new CalcShort(-num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CalcShort) {
            return num == ((CalcShort) obj).num;
        }
        return false;
    }

    @Override
    public String toString() {
        return Short.toString(num);
    }
}
