package expression.generic;

import expression.exceptions.CalculationException;
import expression.generic.type.*;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] ret = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExpressionParser<?> parser;
        switch (mode) {
            case "i" -> parser = new CheckedExpressionParser();
            case "bi" -> parser = new ExpressionParser<CalcBigInt>(Creators.BIG_INT);
            case "d" -> parser = new ExpressionParser<CalcDouble>(Creators.DOUBLE);
            case "u" -> parser = new ExpressionParser<CalcInt>(Creators.INT);
            case "p" -> parser = new ExpressionParser<CalcMod>(Creators.MOD_NUM);
            case "s" -> parser = new ExpressionParser<CalcShort>(Creators.SHORT);
            default -> throw new IllegalStateException("Invalid mode");
        }
        GenericExpression<?> exp = parser.parse(expression);
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        ret[i - x1][j - y1][k - z1] = exp.evaluate(i, j, k).getNum();
                    } catch (Exception e) {}
                }
            }
        }
        return ret;
    }
}
