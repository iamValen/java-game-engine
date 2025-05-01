package engine;
import figures.Point;
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

    
    /**
     * Move o objeto para uma nova posição e camada.
     * 
     * @param dpos Ponto de deslocamento.
     * @param dlayer Variação na camada.
     */
    @Override
    public void move(Point dpos, int dlayer){
        this.layer+=dlayer;
        this.position = position.translacao(dpos.x(), dpos.y());
    }

    /**
     * Rotaciona o objeto.
     * 
     * @param dTheta Ângulo em graus a adicionar à rotação.
     */
    @Override
    public void rotate(double dTheta){
        this.angle+=dTheta;
        this.angle%=360;
    }

    /**
     * Redimensiona o objeto.
     * 
     * @param dScale Fator de escala a adicionar.
     */
    @Override
    public void scale(double dScale){
        this.scale+=dScale;
    }

    /**
     * Retorna a posição do objeto.
     * 
     * @return Ponto representando a posição do objeto.
     */
    @Override
    public Point position(){
        return this.position;
    }

    /**
     * Retorna a camada do objeto.
     * 
     * @return Número da camada.
     */
    @Override
    public int layer(){
        return this.layer;
    }

    /**
     * Retorna o ângulo de rotação do objeto.
     * 
     * @return Ângulo em graus.
     */
    @Override
    public double angle(){
        return this.angle;
    }


    /**
     * Retorna o fator de escala do objeto.
     * 
     * @return Fator de escala.
     */
    @Override
    public double scale(){
        return this.scale;
    }



    @Override
    public String toString(){
        return String.format("%s %d %.2f %.2f", position.toString(), this.layer, this.angle, this.scale);
    }
}
