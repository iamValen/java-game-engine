/**
 * Interface do Transform.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public interface ITransform {
    /**
    * Move this ITransform by dPos.x(), dPos.y() and dlayer
    * @param dPos the 2D differential to move
    * @param dlayer the layer differential
    */
    public void move(Point dPos, int dlayer);

    /**
    * Rotate this ITransform from current orientation by dTheta
    * @param dTheta
    * pos: 0 <= this.angle() < 360
    */
    public void rotate(double dTheta);

    /**
    * increment the ITransform scale by dscale
    * @param dScale the scale increment
    */
    public void scale(double dScale);

    /**
    * @return the (x,y) coordinates
    */
    public Point position();

    /**
    * @return the layer
     */
    public int layer();
    
    /**
     * @return the angle in degrees
     */
    public double angle();

    /**
     * Retorna o ângulo de rotação anterior do objeto.
     * 
     * @return Ângulo em graus.
     */
    public double angleOld();

    /**
     * @return the current scale factor
     */
    public double scale();

    /**
     * Retorna o fator de escala anterior do objeto.
     * 
     * @return Fator de escala.
     */
    public double scaleOld();

    /**
     * Atualiza os valores antigos de ângulo e escala.
     */
    public void update();

    public String toString();
}
