package util;

public class util {
    public static double tol(double d){
        if(-1e-9 <= d && d <= 1e-9) return 0;
        return d;
    }

    public static boolean tolEquals(double d1, double d2){
        if(d1 - 1e-9 <= d2 && d2 <= d1 + 1e-9)
            return true;
        return false;
    }
}
