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
    private double angle, angleOld;
    private double scale, scaleOld;

    /**
     * Construtor da classe Transform.
     * 
     * @param x Posição X inicial.
     * @param y Posição Y inicial.
     * @param layer Camada do objeto.
     * @param rotation Ângulo de rotação inicial.
     * @param scale Fator de escala inicial.
     */
    Transform(double x, double y, int layer, double rotation, double scale){
        this.position = new Point(x,y);
        this.layer = layer;
        this.angle = rotation;
        this.scale = scale;
        angleOld = 0;
        scaleOld = 1;
    }

    /**
     * Move o objeto para uma nova posição e camada.
     * 
     * @param dpos Ponto de deslocamento.
     * @param dlayer Variação na camada.
     */
    public void move(Point dpos, int dlayer){
        layer+=dlayer;
        position = position.translacao(dpos.x(), dpos.y());
    }

    /**
     * Rotaciona o objeto.
     * 
     * @param dTheta Ângulo em graus a adicionar à rotação.
     */
    public void rotate(double dTheta){
        angle+=dTheta;
        angle%=360;
    }

    /**
     * Redimensiona o objeto.
     * 
     * @param dScale Fator de escala a adicionar.
     */
    public void scale(double dScale){
        scale+=dScale;
    }

    /**
     * Retorna a posição do objeto.
     * 
     * @return Ponto representando a posição do objeto.
     */
    public Point position(){
        return position;
    }

    /**
     * Retorna a camada do objeto.
     * 
     * @return Número da camada.
     */
    public int layer(){
        return layer;
    }

    /**
     * Retorna o ângulo de rotação do objeto.
     * 
     * @return Ângulo em graus.
     */
    public double angle(){
        return angle;
    }

    /**
     * Retorna o ângulo de rotação anterior do objeto.
     * 
     * @return Ângulo em graus.
     */
    public double angleOld(){
        return angleOld;
    }

    /**
     * Retorna o fator de escala do objeto.
     * 
     * @return Fator de escala.
     */
    public double scale(){
        return scale;
    }

    /**
     * Retorna o fator de escala anterior do objeto.
     * 
     * @return Fator de escala.
     */
    public double scaleOld(){
        return scaleOld;
    }

    @Override
    public String toString(){
        return String.format("%s %d %.2f %.2f", position.toString(), layer, angle, scale);
    }
}
