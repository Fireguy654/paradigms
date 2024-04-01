package expression.generic.type;

import java.math.BigInteger;
import java.util.Objects;

public class CalcBigInt implements Calculable<CalcBigInt> {
    private final BigInteger num;

    public CalcBigInt(BigInteger num) {
        this.num = num;
    }

    public CalcBigInt(String num) {
        this.num = new BigInteger(num);
    }

    public BigInteger getNum() {
        return num;
    }

    @Override
    public CalcBigInt mod(CalcBigInt oth) {
        return new CalcBigInt(num.mod(oth.num));
    }

    @Override
    public CalcBigInt square() {
        return new CalcBigInt(num.multiply(num));
    }

    @Override
    public CalcBigInt abs() {
        return new CalcBigInt(num.abs());
    }

    @Override
    public CalcBigInt parse(String num) {
        return new CalcBigInt(num);
    }

    @Override
    public CalcBigInt valueOf(int num) {
        return new CalcBigInt(BigInteger.valueOf(num));
    }

    @Override
    public CalcBigInt add(CalcBigInt oth) {
        return new CalcBigInt(num.add(oth.num));
    }

    @Override
    public CalcBigInt sub(CalcBigInt oth) {
        return new CalcBigInt(num.add(oth.num.negate()));
    }

    @Override
    public CalcBigInt mult(CalcBigInt oth) {
        return new CalcBigInt(num.multiply(oth.num));
    }

    @Override
    public CalcBigInt div(CalcBigInt oth) {
        return new CalcBigInt(num.divide(oth.num));
    }

    @Override
    public CalcBigInt neg() {
        return new CalcBigInt(num.negate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CalcBigInt) {
            return num.equals(((CalcBigInt) obj).num);
        }
        return false;
    }

    @Override
    public String toString() {
        return num.toString();
    }
}
