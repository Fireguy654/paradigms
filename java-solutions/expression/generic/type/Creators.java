package expression.generic.type;

import java.math.BigInteger;

public final class Creators {
    public static final CalcInt INT = new CalcInt(0);
    public static final CalcDouble DOUBLE = new CalcDouble(0);
    public static final CalcBigInt BIG_INT = new CalcBigInt(BigInteger.valueOf(0));
    public static final CalcShort SHORT = new CalcShort(0);
    public static final CalcMod MOD_NUM = new CalcMod(0);
}
