package figures;
/**
 * Realiza todas as operações sobre círculos.
 * Representa uma figura circular com centro e raio.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class Circle extends Figure {
    private final Point center;
    private final Double radius;

    /**
     * Cria um círculo dados um ponto e um raio.
     * 
     * @param c ponto correspondente ao centro do círculo
     * @param r raio do círculo
     */
    public Circle(Point c, Double r){
        super(1);
        this.center = c;
        this.radius = r;
    }

    /**
     * Retorna o centro do círculo.
     * 
     * @return o ponto que representa o centro
     */
    public Point center(){ 
        return center; 
    }

    /**
     * Retorna o raio do círculo.
     * 
     * @return o valor do raio
     */
    public Double radius(){ 
        return radius; 
    }

    /**
     * Retorna um novo círculo obtido através da translação do centro pelas 
     * coordenadas dx e dy.
     * 
     * @param dx alteração na coordenada x
     * @param dy alteração na coordenada y
     * @return novo círculo com o centro transladado e mesmo raio
     */
    @Override
    public Circle translation(double dx, double dy){
        Point c = this.center.translacao(dx, dy);
        return new Circle(c, radius);
    }

    /**
     * Retorna um novo círculo obtido através da translação do centro pelo ponto p
     * 
     * @param p ponto que representa o vetor de translação
     * @return novo círculo com o centro transladado e mesmo raio
     */
    @Override
    public Figure translation(Point p) {
        Point c = this.center.sum(p);
        return new Circle(c, radius);
    }

    /**
     * Verifica se o segmento dado interseta o círculo
     * 
     * @param s segmento a ser testado
     * @return true se o segmento intersetar o círculo, senao false
     */
    public boolean intersetaSegmento(Segment s){
        Point AB = s.p1().vetor(s.p2());
        Point AC = s.p1().vetor(this.center);
        double t = Point.vetorPI(AB, AC) / Point.vetorPI(AB, AB);
        if(t <= 0){
            if(s.p1().distance(this.center) <= this.radius)
                return true;
        }
        if(t >= 1){
            if(s.p2().distance(this.center) <= this.radius)
                return true;
        }
        if(0 < t && t < 1){
            if (new Point(s.p1(), s.p2(), t).distance(this.center) <= this.radius)
                return true;
        }
        return false;
    }

    /**
     * Verifica se este círculo colide com outro círculo dado
     * 
     * @param that outro círculo a ser testado para colisão
     * @return true se os círculos colidirem, senao false
     */
    public boolean colisaoCirculo(Circle that){
        return(that.center.distance(this.center) < that.radius + this.radius);
    }

    /**
     * Verifica se este círculo colide com um polígono dado
     * 
     * @param that polígono a ser testado para colisão
     * @return true se houver colisão entre o círculo e o polígono, senao false
     */
    public boolean collisionPolygon(Polygon that){
        for(Segment s : that.segmentos()){
            if(this.intersetaSegmento(s))
                return true;
        }
        if(this.center.distance(that.pontos[0]) <= this.radius)
            return true;
        return(that.containsPoint(this.center));
    }

    /**
     * Retorna o centro do círculo, que é o seu centro.
     * 
     * @return o ponto centroide do círculo
     */
    @Override
    public Point centroid(){
        return new Point(center.x(), center.y());
    }

    /**
     * Retorna uma nova figura (círculo) escalada pelo fator r.
     * 
     * @param s fator de escala a ser aplicado no raio
     * @return novo círculo com o raio escalado
     */
    @Override
    public Figure scale(double s){
        return new Circle(this.center.scale(s), this.radius * s);
    }

    /**
     * Metodo rotate no círculo
     * 
     * @param r ângulo de rotação (não utilizado)
     * @return este mesmo círculo, pois a rotação não o altera
     */
    @Override
    public Figure rotate(double r){
        return new Circle(this.center.rotate(r), this.radius);
    }

    /**
     * Verifica se este círculo colide com outra figura dada
     * 
     * @param that outra figura
     */
    @Override
    public boolean collision(Figure that) {
        if (that.tipoFig == 1) { // Círculo vs Círculo
            return this.colisaoCirculo((Circle) that);
        } else if (that.tipoFig == 0) { // Círculo vs Polígono
            return this.collisionPolygon((Polygon) that);
        }
        return that.collision(this);
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", center.toString(), radius);
    }
}
