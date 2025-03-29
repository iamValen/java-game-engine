/**
 * ...
 * @author Alexandre Menino a83974
 * @version 28/03/2025
 */
public class Collider implements ICollider {
    private Figura fig;
    private Point centroid;
    private double scale;
    private int layer;

    Collider(Figura fig, Transform transform){

        fig = fig.translacao(fig.centroid().flipSign()); //vai para a posicao 0,0
        
        fig = fig.rotate(transform.angle()); //rodada

        fig = fig.scale(transform.scale()); //escalada
        scale = transform.scale();

        this.centroid = transform.position();
        
        this.fig = fig.translacao(centroid); //vai para a posicao certa

        this.layer = transform.layer();
    }

    public void move(Point p, int layer){
        fig = fig.translacao(p.x(), p.y());
        centroid = centroid.sum(p);
        this.layer += layer;
    }

    public void rotate(double r){
        fig = fig.translacao(centroid.flipSign());
        fig = fig.rotate(r);
        fig = fig.translacao(centroid);
    }

    public void scale(double s){
        fig = fig.translacao(centroid.flipSign());
        fig = fig.scale(1f + s/scale);
        scale += s;
        fig = fig.translacao(centroid);
    }


    public Point centroid(){
        return centroid;
    }

    @Override
    public String toString(){
        return fig.toString();
    }
}
