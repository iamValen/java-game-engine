public class Collider implements ICollider {
    private Figura fig;
    int layer;

    Collider(Figura fig, Transform transform){

        fig = fig.translacao(fig.centroid().flipSign()); //vai para a posicao 0,0
        
        fig = fig.rotate(transform.angle()); //rodada
        
        fig = fig.scale(transform.scale()); //escalada

        this.fig = fig.translacao(transform.position()); //vai para a posicao certa

        this.layer = transform.layer();
    }

    public void move(Point p, int layer){
        fig.translacao(p.x(), p.y());
        this.layer += layer;
    }

    public Point centroid(){
        return fig.centroid();
    }
}
