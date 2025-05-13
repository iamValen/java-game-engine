package util;

/**
 * Extra functions for the project
 * @author Alexandre Menino a83974
 * @version 28/03/2025
 */
public class util {
    public static double tolZero(double d){
        if(-1e-9 <= d && d <= 1e-9) return 0;
        return d;
    }

    public static boolean tolEquals(double d1, double d2){
        return d1 - 1e-9 <= d2 && d2 <= d1 + 1e-9;
    }
}
