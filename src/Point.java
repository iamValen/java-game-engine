/**
 * Represents a point in two dimensions
 * @author Valentim Khakhitva
 * @version (27/2/2025)
 * @inv if a point is outside the 1st quadrant
 */
public class Point {
    private int x, y;
    private double r, t;

    /**
     * @param x Abscissa of the point
     * @param y Ordinate of the point
     */
    public Point(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Ponto:vi");
        }
        this.x = x;
        this.y = y;
        updatePolar();
    }

    /**
     * @param r Radius of the point
     * @param t Angle of the point
     */
    public Point(double r, double t) {
        t = t % 360;
        if (t > 90 || t < 0 || r > 10) {
            throw new IllegalArgumentException("Ponto:vi");
        }
        this.r = r;
        this.t = Math.toRadians(t);
        updateCartesian();
    }

    public int x(){return x;}
    public int y(){return y;}

    public double r(){return r;}
    public double t(){return t;}


    /**
     * Updates the polar variables of the point
     */
    private void updatePolar() {
        this.r = Math.sqrt(x * x + y * y);
        this.t = Math.atan2(y, x);
    }

    /**
     * Updates the cartesian variables of the point
     */
    private void updateCartesian() {
        this.x = (int) Math.round(r * Math.cos(t));
        this.y = (int) Math.round(r * Math.sin(t));
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public double getR() { return this.r; }
    public double getT() { return Math.toDegrees(this.t); }

    /**
     * Calculates the distance between a point and the current point
     * @param that - another point
     * @return distance between the points
     */
    public double distance(Point that) {
        double dx = that.getX() - this.x;
        double dy = that.getY() - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Determines the orientation of 3 points
     * @param b Point 
     * @param c Point 
     * @return null - if collinear; true if clockwise; false if counter-clockwise
     */
    public Boolean orientation(Point b, Point c) {
        int val = ((b.y - this.y) * (c.x - b.x)) - ((b.x - this.x) * (c.y - b.y));
        if (val == 0) return null; // Collinear
        return val > 0; // true if Clockwise, false if CounterClockwise
    }

    /**
     * Translates a point
     * @param dx
     * @param dy
     * @return
     */
    public Point translate(int dx, int dy) {
        return new Point(this.x + dx, this.y + dy);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
