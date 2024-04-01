package expression;

import expression.exceptions.IntOverflowException;

public final class MathFuncs {
    public static int pow(int base, int power) {
        if (power == 0) {
            return 1;
        }
        if (power % 2 == 0) {
            int ans = pow(base, power / 2);
            if (isMultOverflowed(ans, ans)) {
                throw new IntOverflowException("Overflow in pow");
            }
            return ans * ans;
        } else {
            int ans = pow(base, power - 1);
            if (isMultOverflowed(ans, base)) {
                throw new IntOverflowException("Overflow in pow");
            }
            return ans * base;
        }
    }

    public static int abs(int arg) {
        if (arg < 0 && arg != Integer.MIN_VALUE) {
            return arg * -1;
        } else {
            return arg;
        }
    }

    public static int getGCD(int fir, int sec) {
        while (sec != 0) {
            fir %= sec;
            int tmp = sec;
            sec = fir;
            fir = tmp;
        }
        return abs(fir);
    }

    public static boolean isSumOverflowed(int fir, int sec) {
        return sec >= 0 && fir > Integer.MAX_VALUE - sec || sec < 0 && fir < Integer.MIN_VALUE - sec;
    }

    public static boolean isSubOverflowed(int fir, int sec) {
        return sec < 0 && fir > Integer.MAX_VALUE + sec || sec > 0 && fir < Integer.MIN_VALUE + sec;
    }

    public static boolean isDivOverflowed(int fir, int sec) {
        return fir == Integer.MIN_VALUE && sec == -1;
    }

    public static boolean isMultOverflowed(int fir, int sec) {
        return fir > 0 && sec > 0 && fir > Integer.MAX_VALUE / sec ||
            fir < 0 && sec < 0 && fir < Integer.MAX_VALUE / sec ||
            fir > 0 && sec < 0 && sec != -1 && fir > Integer.MIN_VALUE / sec ||
            fir < 0 && sec > 0 && fir < Integer.MIN_VALUE / sec;
    }
}
