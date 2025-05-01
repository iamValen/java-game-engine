package engine;
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
    private double maxSpeed;
    private int layerSpeed;
    private double rotationSpeed;
    private double scaleSpeed;

    public void update(){
        this.speed = speed.sum(accel);
    }

    public Point Speed(){
        return this.speed;
    }

    public Point Accel(){ return this.accel; }

    public double MaxSpeed(){
        return this.maxSpeed;
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

    public void setSpeed(double x, double y){
        this.speed = new Point(x, y);
    }
    
    public void setLayerSpeed(int layerSpeed){
        this.layerSpeed = layerSpeed;
    }
}
