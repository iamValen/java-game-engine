package behaviour;
import figures.Point;

/**
 * very basic implementation of physics
 * more should be addded later
 * ex: speed cap, starting speed, gravity, friction...
 * not even used yet
 */

public class Physics {
    private Point speed;
    private Point accel;
    private int layerSpeed;
    private double rotationSpeed;
    private double scaleSpeed;

    private final double gravity = 125;

    public Physics(){
        accel = new Point(0, gravity);
        speed = new Point(0, 0);
    }


    public void update(double  dT){


        this.speed = speed.sum(accel.scale(dT));

        this.speed = speed.scale(0.8* dT*(1/0.016666)); //atrito

        this.accel = new Point(0, gravity);
    }

    public Point Speed(){
        return this.speed;
    }

    public Point Accel(){
        return this.accel;
    }

    public int LayerSpeed(){
        return this.layerSpeed;
    }

    public double RotationSpeed(){
        return this.rotationSpeed;
    }

    public double ScaleSpeed(){
        return this.scaleSpeed;
    }


    public void setAccel(double x, double y){
        this.accel = new Point(x, y);
    }

    public void sumAccel(double x, double y){
        this.accel = this.accel.sum(new Point(x, y));
    }

    public void setSpeed(double x, double y){
        this.speed = new Point(x, y);
    }
    
    public void setLayerSpeed(int layerSpeed){
        this.layerSpeed = layerSpeed;
    }
}
