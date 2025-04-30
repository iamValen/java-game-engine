/**
 * very basic implementation of physics
 * more should be addded later
 * ex: speed cap, starting speed, gravity, friction...
 * not even used yet
 */

public class physics {
    private Point speed;
    private Point accel;

    public void update(){
        this.speed = speed.sum(accel);
    }

    public void setAccel(double x, double y){
        this.accel = new Point(x, y);
    }

    public Point Speed(){
        return this.speed;
    }
    public Point Accel(){
        return this.accel;
    }
}
