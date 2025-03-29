/**
 * ...
 * @author Alexandre Menino a83974
 * @version 28/03/2025
 */
public class Transform implements ITransform {

    private Point posicao;
    private int layer;
    private double rotacao;
    private double escala;

    Transform(double x, double y, int layer, double rotation, double scale){
        this.posicao = new Point(x,y);
        this.layer = layer;
        this.rotacao = rotation;
        this.escala = scale;
    }

    public void move(Point dpos, int dlayer){
        layer+=dlayer;
        posicao = posicao.translacao(dpos.x(), dpos.y());
    }

    public void rotate(double dTheta){
        rotacao+=dTheta;
        rotacao%=360;
    }

    public void scale(double dScale){
        escala+=dScale;
    }

    public Point position(){
        return posicao;
    }

    public int layer(){
        return layer;
    }

    public double angle(){
        return rotacao;
    }

    public double scale(){
        return escala;
    }

    @Override
    public String toString(){
        return String.format("%s %d %.2f %.2f", posicao.toString(), layer, rotacao, escala);
    }
}
