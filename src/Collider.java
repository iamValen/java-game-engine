public class Collider implements ICollider {
    private Figura fig;
    private final Transform transform;
    private Point centroid;
    private double angle;
    private double scale;


    Collider(Figura fig, Transform transform) {
        this.fig = fig;
        this.transform = transform;
        this.centroid = fig.centroid();
        this.angle = 0;
        this.scale = 1;
        updateFig();
    }

    public void updateFig() {
        fig = fig.translacao(centroid.flipSign()); // Move para a origem
        fig = fig.rotate(transform.angle() - angle); // Aplica rotação
        angle = transform.angle();
        fig = fig.scale(transform.scale() / scale); // Aplica escala
        scale = transform.scale();
        centroid = transform.position();
        fig = fig.translacao(centroid); // Move para a posição correta
    }

    // public void move(Point p, int layer) {
    //     transform.move(p, layer);
    //     updateFig();
    // }

    // public void rotate(double r) {
    //     transform.rotate(r);
    //     updateFig();
    // }

    // public void scale(double s) {
    //     transform.scale(s);
    //     updateFig();
    // }

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
        return this.fig.colisao(that.fig);
    }

    @Override
    public String toString() {
        return fig.toString();
    }
}
