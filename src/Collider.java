/**
 * Representa o colisor de um objeto.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class Collider implements ICollider {
    private Figure fig;
    private final ITransform transform;
    private Point centroid;

    /**
     * Construtor da classe Collider.
     * 
     * @param fig Figura associada ao collider.
     * @param transform Transformação associada ao collider.
     */
    Collider(Figure fig, ITransform transform) {
        this.fig = fig;
        this.transform = transform;
        this.centroid = fig.centroid();
        updateFig();
    }

    /**
     * Atualiza a figura com base na transformação aplicada.
     * 
     */
    public void updateFig() {
        fig = fig.translation(centroid.flipSign()); // Move para a origem
        fig = fig.rotate(transform.angle() - transform.angleOld()); // Aplica rotação
        fig = fig.scale(transform.scale() / transform.scaleOld()); // Aplica escala
        centroid = transform.position();
        fig = fig.translation(centroid); // Move para a posição correta
        transform.update();
    }

    /**
     * Retorna o centroid da
     * 
     * @return ponto que representa o centroide da figura.
     */
    public Point centroid() {
        return centroid;
    }

    /**
     * Verifica se este Collider colide com outro Collider.
     * 
     * @param other Outro Collider a ser testado.
     * @return true se houver colisão, false caso contrário.
     */
    public boolean collidesWith(ICollider other) {
        Collider that = (Collider) other;
        return this.fig.collision(that.fig);
    }

    @Override
    public String toString() {
        return fig.toString();
    }
}
