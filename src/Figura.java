
public abstract class Figura {
    public final int tipoFig;
    /*
     * 0 - Poligono
     * 1 - Circulo
     */

    protected Figura(int tipo){
            this.tipoFig = tipo;
    }

    public abstract Point centroid();

    public abstract String toString();

    public abstract Figura translacao(double dx, double dy);

    public abstract Figura translacao(Point p);
}
