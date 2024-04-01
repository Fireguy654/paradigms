package expression.generic;

import expression.exceptions.ParseIntegerException;
import expression.generic.type.CalcInt;
import expression.generic.type.Creators;

public class CheckedExpressionParser extends ExpressionParser<CalcInt> {

    public CheckedExpressionParser() {
        super(Creators.INT);
    }

    @Override
    protected GenericExpression<CalcInt> create(String oper,
                                                GenericExpression<CalcInt> fir,
                                                GenericExpression<CalcInt> sec) {
        GenericExpression<CalcInt> ret = null;
        switch (oper) {
            case "add" -> ret = new CheckedAdd(fir, sec);
            case "sub" -> ret = new CheckedSubtract(fir, sec);
            case "mult" -> ret = new CheckedMultiply(fir, sec);
            case "div" -> ret = new CheckedDivide(fir, sec);
            case "mod" -> ret = new CheckedMod(fir, sec);
            case "neg" -> ret = new CheckedNegate(fir);
            case "abs" -> ret = new CheckedAbs(fir);
            case "square" -> ret = new CheckedSquare(fir);
        }
        return ret;
    }

    @Override
    protected CalcInt parseT(StringBuilder numSB) {
        while (testDigit()) {
            numSB.append(take());
        }
        try {
            return creator.parse(numSB.toString());
        } catch (NumberFormatException ex) {
            throw new ParseIntegerException("Expected number of integer type, found: '" + numSB + "'", ex);
        }
    }
}
