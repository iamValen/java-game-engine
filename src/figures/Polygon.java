package figures;
import static util.util.*;

/**
 * Realiza todas as operacoes sobre Poligonos
 * @author Alexandre Menino a83974
 * @version 1.0 22/03/2025
 * @inv invariante e um poligono com pontos seguidos colineares ou com segmentos que se intersetam
 */
public class Polygon extends Figure {
    protected final Point[] pontos;
    protected final Segment[] segmentos;
    @SuppressWarnings("unused")
    private final int tipoPol;
    /*
     * 0 - N lados
     * 3 - Triangulo
     * 4 - Retangulo
     */

    /**
     * Cria um poligono dado um numero de pontos e um tipo
     * 
     * @param arr array de pontos que formam o poligono
     * @param tipo tipo do poligono
     */
    protected Polygon(Point[] arr, int tipo){
        super(0);
        this.tipoPol = tipo;
        segmentos = new Segment[arr.length];
        for(int i = 0; i < arr.length; i++){
            segmentos[i] = new Segment(arr[i], arr[(i+1)%arr.length]);
        }
        pontos = arr;
        checkPol();
    }

    /**
     * Cria um poligono de N lados dado um array de pontos
     * 
     * @param arr array de Ponto
     */
    public Polygon(Point[] arr){
        this(arr, 0);
    }

    /**
     * Verifica se o poligono tem pelo menos 3 lados, nao ha lados colineares consecutivos
     * e os seus segmentos nao se intersetam
     */
    private void checkPol(){
        if(pontos.length < 3) throw new IllegalArgumentException("Poligono:vi");

        for(int i = 0; i < pontos.length; i++) {
            if(Point.collinear(pontos[i], pontos[(i+1)%pontos.length], pontos[(i+2)%pontos.length]))
                throw new IllegalArgumentException("Poligono:vi");
        }

        for(int i = 0; i < pontos.length; i++){
            for(int j = i+2; j < pontos.length; j++){
                Segment a = segmentos[i];
                Segment b = segmentos[j%pontos.length];
                if(a.intersects(b)){
                    throw new IllegalArgumentException("Poligono:vi");
                }
            }
        }
    }

    public Segment[] segmentos(){ return segmentos; }

    public Point[] pontos(){ return pontos; }

    /**
     * Verifica se um ponto esta dentro do poligono
     * 
     * @param p ponto dado
     * @return true se estiver dentro, senao false
     */
    public boolean containsPoint(Point p){
        Point AP;
        Point AB;
        int sign[] = new int[this.pontos.length];
        for(int i = 0; i < this.pontos.length; i++){
            AP = this.pontos[i].vetor(p);
            AB = this.pontos[i].vetor(this.pontos[(i+1)%this.pontos.length]);
            sign[i] = (int) Math.signum(tol(Point.vetorPE(AP, AB)));
        }
        for(int i = 0; i < this.pontos.length; i++){
            if(sign[i] != sign[(i+1)%this.pontos.length])
                return false;
        }
        return true;
    }

    /**
     * Verifica a colisao do poligono com um circulo dado
     * 
     * @param that circulo dado
     * @return true se colidirem, senao false
     */
    public boolean collisionCircle(Circle that){
        return that.collisionPolygon(this);
    }

    /**
     * Verifica a colisao do poligono com um poligono dado
     * 
     * @param that poligono dado
     * @return true se colidirem, senao false
     */
    public boolean collisionPolygon(Polygon that){
        for(int i = 0; i < this.pontos.length; i++){
            if(that.containsPoint(this.pontos[i]))
                return true;
        }
        for(int j = 0; j < that.pontos.length; j++){
            if(this.containsPoint(that.pontos[j]))
                return true;
        }
        for(int i = 0; i < this.segmentos.length; i++){
            for(int j = 0; j < that.segmentos.length; j++){
                if(this.segmentos[i].intersects(that.segmentos[j]))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean collision(Figure that) {
        if (that.tipoFig == 0) { // Polígono vs Polígono
            return this.collisionPolygon((Polygon) that);
        } else if (that.tipoFig == 1) { // Polígono vs Círculo
            return this.collisionCircle((Circle) that);
        }
        return false;
    }

    /**
     * Cria um novo array de pontos baseado nos pontos do poligono e na sua alteracao de posicao dx e dy
     * 
     * @param dx diferenca de posicao no eixo x
     * @param dy diferenca de posicao no eixo y
     * @return array de Ponto com os novos pontos
     */
    protected  Point[] translatePoints(double dx, double dy){
        Point[] out = new Point[this.pontos.length];
        for(int i = 0; i < out.length; i++){
            out[i] = this.pontos[i].translacao(dx, dy);
        }
        return out;
    }

    /**
     * Cria um novo poligono sendo este a translacao do recetor
     * 
     * @param dx diferenca de posicao no eixo x
     * @param dy diferenca de posicao no eixo y
     * @return novo poligono
     */
    @Override
    public Polygon translation(double dx, double dy){
        Point[] pts = translatePoints(dx, dy);
        return new Polygon(pts);
    }

    /** 
     * Retorna um novo círculo obtido através da translação do centro pelo ponto p
     * 
     * @param p
     * @return novo poligono
     */
    @Override
    public Polygon translation(Point p){
        Point[] pts = translatePoints(p.x(), p.y());
        return new Polygon(pts);
    }

    /**
     * Retorna o centroid do polígono
     */
    @Override
    public Point centroid(){

        Polygon p = this;

        double area = 0;
        for(int i = 0; i < p.pontos.length; i++){
            area += p.pontos[i].x()*p.pontos[(i+1)%p.pontos.length].y();
            area -= p.pontos[(i+1)%p.pontos.length].x()*p.pontos[i].y();
        }
        area/=2;



        double xc = 0;
        for(int i = 0; i < p.pontos.length; i++){
            xc+= (p.pontos[i].x()+p.pontos[(i+1)%p.pontos.length].x())
            *
            (
                p.pontos[i].x()*p.pontos[(i+1)%p.pontos.length].y()
            -
                p.pontos[(i+1)%p.pontos.length].x()*p.pontos[i].y());
        }
        xc/=6*area;

        double yc = 0;
        for(int i = 0; i < p.pontos.length; i++){
            yc+= (p.pontos[i].y()+p.pontos[(i+1)%p.pontos.length].y())
            *
            (
                p.pontos[i].x()*p.pontos[(i+1)%p.pontos.length].y()
            -
                p.pontos[(i+1)%p.pontos.length].x()*p.pontos[i].y());
        }
        yc/=6*area;

        return new Point(xc, yc);
    }

    /**
     * Escala o polígono multiplicando a posição de cada ponto pelo fator de escala s.
     * Isso resulta em um novo polígono proporcionalmente maior ou menor, dependendo do valor de s.
     * 
     * @param s fator de escala (s > 1 aumenta o tamanho, 0 < s < 1 reduz o tamanho)
     * @return novo polígono escalado
     */
    @Override
    public Figure scale(double s){
        Point[] pts = new Point[pontos.length];

        for(int i = 0; i < pontos.length; i++){
            pts[i] = pontos[i].scale(s);
        }

        return new Polygon(pts);
    }

    /**
     * Rotaciona o polígono em torno da origem por um ângulo r (em radianos).
     * Cada ponto do polígono será rotacionado individualmente usando a transformação de rotação.
     * 
     * @param r ângulo de rotação em radianos (positivo para sentido anti-horário, negativo para horário)
     * @return novo polígono rotacionado
     */
    @Override
    public Figure rotate(double r){
        Point[] pts = new Point[pontos.length];

        for(int i = 0; i < pontos.length; i++){
            pts[i] = pontos[i].rotate(r);
        }

        return new Polygon(pts);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Point ponto : pontos) {
            sb.append(String.format("%s ", ponto.toString()));
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
