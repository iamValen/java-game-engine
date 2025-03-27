public class Collider implements ICollider {
    private Figura fig;
    private Point centroid;
    private int layer;

    Collider(Figura fig, Transform transform){

        fig = fig.translacao(fig.centroid().flipSign()); //vai para a posicao 0,0
        
        fig = fig.rotate(transform.angle()); //rodada

        fig = fig.scale(transform.scale()); //escalada

        this.centroid = transform.position();
        
        this.fig = fig.translacao(centroid); //vai para a posicao certa

        this.layer = transform.layer();
    }

    public void move(Point p, int layer){
        fig.translacao(p.x(), p.y());
        centroid.sum(p);
        this.layer += layer;
    }

    public void rotate(double r){
        fig.translacao(centroid.flipSign());
        fig.rotate(r);
        fig.translacao(centroid);
    }

    public void scale(double s){
        fig.translacao(centroid.flipSign());
        fig.scale(s);
        fig.translacao(centroid);
    }


    public Point centroid(){
        return centroid;
    }
}
