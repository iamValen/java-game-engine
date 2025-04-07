public class Collider implements ICollider {
    private Figura fig;
    private final Transform transform;

    Collider(Figura fig, Transform transform) {
        this.transform = transform;
        this.fig = fig.translacao(fig.centroid().flipSign()); // Move para a posição (0,0)
        updateFig();
    }

    private void updateFig() {
        fig = fig.translacao(fig.centroid().flipSign()); // Move para a origem
        fig = fig.rotate(transform.angle()); // Aplica rotação
        fig = fig.scale(transform.scale()); // Aplica escala
        fig = fig.translacao(transform.position()); // Move para a posição correta
    }

    public void move(Point p, int layer) {
        transform.move(p, layer);
        updateFig();
    }

    public void rotate(double r) {
        transform.rotate(r);
        updateFig();
    }

    public void scale(double s) {
        transform.scale(s);
        updateFig();
    }

    public Point centroid() {
        return transform.position();
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
