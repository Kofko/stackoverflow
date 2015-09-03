/**
 * http://stackoverflow.com/questions/24847701/efficient-10-power-x-algorithm
 * <p/>
 * Can anyone help me with finding an efficient code to find 10 power x?
 */
public class TenPower {
    public static void main(String[] args) {
        System.out.println(fastTenPow(19.123));
        System.out.println(Math.pow(10., 19.123));
    }

    public static double fastTenPow(double x) {
        if (x < 0.) {
            return 1. / fastTenPow(-x);
        }
        int intPart = (int) x;
        double fractPart = x - intPart;
        return fractTenPow(fractPart) * intTenPow(intPart);
    }

    private static double intTenPow(int x) {
        double res = 1.;
        double base = 10.;
        while (x != 0) {
            if ((x & 1) == 1) {
                res *= base;
            }
            x >>= 1;
            base *= base;
        }
        return res;
    }

    private static final double LOG_TEN = 2.302585;
    private static final double EPS = 0.0000001;

    private static double fractTenPow(double x) {
        double sum = 0.;
        x *= LOG_TEN;
        double tmp = 1.;
        for (double i = 1.; tmp > EPS; i += 1.) {
            sum += tmp;
            tmp *= x;
            tmp /= i;
        }
        return sum;
    }
}
