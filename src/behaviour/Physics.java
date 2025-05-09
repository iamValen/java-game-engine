package behaviour;
import figures.*;
import interfaces.IGameObject;

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

    private final Point gravity = new Point(0, 160);

    public Physics(){
        accel = gravity;
        speed = new Point(0, 0);
    }


    public void update(double  dT){


        this.speed = speed.sum(accel.scale(dT));

        this.speed = speed.scale(0.85 / (dT/0.016666)); //atrito

        this.accel = gravity;
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

    /**
     * evita um objeto se sobreponha com o chao
     * @param GOm objeto q vai mover
     * @param GOp objeto parado
     */
    public static void snapToFloor(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().getHitbox();
        Figure p = GOp.collider().getHitbox();
        double lowest = m.maxY();
        double highest = p.minY();

        GOm.transform().move(new Point(0, highest - lowest), 0);
    }

    public static void snapToCeling(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().getHitbox();
        Figure p = GOp.collider().getHitbox();
        double highest = m.minY();
        double lowest = p.maxY();

        GOm.transform().move(new Point(0, lowest - highest), 0);
    }

    public static void snapToWallOnTheLeft(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().getHitbox();
        Figure p = GOp.collider().getHitbox();
        double leftest = m.minX();
        double rightest = p.maxX();

        GOm.transform().move(new Point(rightest - leftest, 0), 0);
    }

    public static void snapToWallOnTheRight(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().getHitbox();
        Figure p = GOp.collider().getHitbox();
        double rightest = m.maxX();
        double leftest = p.minX();

        GOm.transform().move(new Point(leftest - rightest, 0), 0);
    }
}
