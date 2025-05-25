package engine;
import geometry.Point;
import interfaces.ITransform;

/**
 * Contém toda a informaçao da posicao de um GameObject
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class Transform implements ITransform {
    private Point position;
    private int layer;
    private double angle;
    private double scale;
    private int direction;

    /**
     * Construtor da classe Transform.
     * 
     * @param x Posição X inicial.
     * @param y Posição Y inicial.
     * @param layer Camada do objeto.
     * @param rotation Ângulo de rotação inicial.
     * @param scale Fator de escala inicial.
     */
    public Transform(double x, double y, int layer, double rotation, double scale){
        this.position = new Point(x,y);
        this.layer = layer;
        this.angle = rotation;
        this.scale = scale;
    }

    @Override
    public void setDirection(int i){
        this.direction = i;
    }

    @Override
    public int direction() {
        return this.direction;
    }
    
    @Override
    public void move(Point dpos, int dlayer){
        this.layer+=dlayer;
        this.position = position.translacao(dpos.x(), dpos.y());
    }

    @Override
    public void setPosition(Point pos, int layer){
        this.layer=layer;
        this.position = pos;
    }

    @Override
    public void rotate(double dTheta){
        this.angle+=dTheta;
        this.angle%=360;
    }

    @Override
    public void scale(double dScale){
        this.scale+=dScale;
    }

    @Override
    public Point position(){
        return this.position;
    }

    @Override
    public int layer(){
        return this.layer;
    }

    @Override
    public double angle(){
        return this.angle;
    }

    @Override
    public double scale(){
        return this.scale;
    }


    @Override
    public String toString(){
        return String.format("%s %d %.2f %.2f", position.toString(), this.layer, this.angle, this.scale);
    }
}
