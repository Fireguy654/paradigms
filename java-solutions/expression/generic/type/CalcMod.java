package expression.generic.type;

import expression.exceptions.DivisionByZeroException;
import expression.generic.Mod;

import java.util.Objects;

public class CalcMod implements Calculable<CalcMod> {
    private static final int MOD = 10079;
    private final int num;

    public CalcMod(int num) {
        this.num = (num % MOD + MOD) % MOD;
    }

    public CalcMod(String num) {
        this.num = (Integer.parseInt(num) % MOD + MOD) % MOD;
    }

    @Override
    public CalcMod parse(String num) {
        return new CalcMod(num);
    }

    @Override
    public CalcMod valueOf(int num) {
        return new CalcMod(num);
    }

    @Override
    public Integer getNum() {
        return num;
    }

    @Override
    public CalcMod add(CalcMod oth) {
        return new CalcMod(num + oth.num);
    }

    @Override
    public CalcMod sub(CalcMod oth) {
        return new CalcMod(num - oth.num + MOD);
    }

    @Override
    public CalcMod mult(CalcMod oth) {
        return new CalcMod(num * oth.num);
    }

    @Override
    public CalcMod div(CalcMod oth) {
        if (oth.num == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        for (int i = 0; i < MOD; i++) {
            if (oth.num * i % MOD == num) {
                return new CalcMod(i);
            }
        }
        throw new IllegalStateException("Didn't found div");
    }

    @Override
    public CalcMod mod(CalcMod oth) {
        return new CalcMod(num % oth.num);
    }

    @Override
    public CalcMod square() {
        return new CalcMod(num * num);
    }

    @Override
    public CalcMod abs() {
        return new CalcMod(num);
    }

    @Override
    public CalcMod neg() {
        return new CalcMod(num * -1 + MOD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CalcMod) {
            return num == ((CalcMod) obj).num;
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(num);
    }
}
