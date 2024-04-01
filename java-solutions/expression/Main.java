package expression;

import expression.exceptions.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in).useDelimiter("\\A");
        String expression = "0";
        if (inp.hasNext()) {
            expression = inp.next();
        }
        TripleExpression parsedExp;
        try {
            parsedExp = new ExpressionParser().parse(expression);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        System.out.println("x\tf");
        for (int i = 0; i <= 10; i++) {
            System.out.print(i + ":\t");
            try {
                System.out.println(parsedExp.evaluate(i, 0, 0));
            } catch (IntOverflowException ex) {
                System.out.println("overflow");
            } catch (DivisionByZeroException ex) {
                System.out.println("division by zero");
            } catch (CalculationException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
