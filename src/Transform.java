/**
 * Contém toda a informaçao da posicao de um GameObject
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class Transform implements ITransform {
    private Point posicao;
    private int layer;
    private double rotacao;
    private double escala;
    
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
        this.posicao = new Point(x,y);
        this.layer = layer;
        this.rotacao = rotation;
        this.escala = scale;
    }

    /**
     * Move o objeto para uma nova posição e camada.
     * 
     * @param dpos Ponto de deslocamento.
     * @param dlayer Variação na camada.
     */
    public void move(Point dpos, int dlayer){
        layer+=dlayer;
        posicao = posicao.translacao(dpos.x(), dpos.y());
    }

    /**
     * Rotaciona o objeto.
     * 
     * @param dTheta Ângulo em graus a adicionar à rotação.
     */
    public void rotate(double dTheta){
        rotacao+=dTheta;
        rotacao%=360;
    }

    /**
     * Redimensiona o objeto.
     * 
     * @param dScale Fator de escala a adicionar.
     */
    public void scale(double dScale){
        escala+=dScale;
    }

    /**
     * Retorna a posição do objeto.
     * 
     * @return Ponto representando a posição do objeto.
     */
    public Point position(){
        return posicao;
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
        return rotacao;
    }

    /**
     * Retorna o fator de escala do objeto.
     * 
     * @return Fator de escala.
     */
    public double scale(){
        return escala;
    }

    @Override
    public String toString(){
        return String.format("%s %d %.2f %.2f", posicao.toString(), layer, rotacao, escala);
    }
}
