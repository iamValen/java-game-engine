public class Collider implements ICollider {
    private Figura fig;
    int layer;

    Collider(Figura fig, Transform transform){

        fig = fig.translacao(fig.centroid().flipSign()); //vai para a posicao 0,0
        
        //TODO: scale, estava a pensar fzr na classe figura

        //TODO: rotate, estava a pensar fzr na classe figura

        this.fig = fig.translacao(transform.position()); //vai para a posicao certa

        this.fig = fig;

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
