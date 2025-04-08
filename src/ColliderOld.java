/**
 * Representa a hitbox de um objeto
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class ColliderOld implements ICollider {
    private Figura fig;
    private Point centroid;
    private double scale;
    private int layer;

    /**
     * Construtor da classe Collider.
     * 
     * @param fig A figura representando a hitbox.
     * @param transform O objeto de transformação com posição, escala e rotação.
     */
    Collider(Figura fig, Transform transform){
        fig = fig.translacao(fig.centroid().flipSign()); // Move para a posição (0,0)
        fig = fig.rotate(transform.angle()); // Aplica rotação
        fig = fig.scale(transform.scale()); // Aplica escala
        scale = transform.scale();

        this.centroid = transform.position();
        this.fig = fig.translacao(centroid); // Move para a posição correta
        this.layer = transform.layer();
    }

    /**
     * Move a hitbox para uma nova posição e camada.
     * 
     * @param p Ponto para onde a hitbox será movida.
     * @param layer Quantidade a adicionar à camada atual.
     */
    public void move(Point p, int layer){
        fig = fig.translacao(p.x(), p.y());
        centroid = centroid.sum(p);
        this.layer += layer;
    }

    /**
     * Rotaciona a hitbox em torno de seu centro.
     * 
     * @param r Ângulo em graus pelo qual a hitbox será rotacionada.
     */
    public void rotate(double r){
        fig = fig.translacao(centroid.flipSign());
        fig = fig.rotate(r);
        fig = fig.translacao(centroid);
    }

    /**
     * Redimensiona a hitbox.
     * 
     * @param s Fator de escala relativo à escala atual.
     */
    public void scale(double s){
        fig = fig.translacao(centroid.flipSign());
        fig = fig.scale(1f + s/scale);
        scale += s;
        fig = fig.translacao(centroid);
    }

    /**
     * Retorna o centro da hitbox.
     * 
     * @return O ponto central da hitbox.
     */
    public Point centroid(){
        return centroid;
    }

    @Override
    public String toString(){
        return fig.toString();
    }
}