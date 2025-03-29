import static utili.util.*;

/**
 * realiza todas as operacoes sobre pontos
 * @author Alexandre Menino a83974
 * @version 8.0 22/03/2025
 * @inv invariante e um ponto fora do 1º quadrante
 */
public class Point {

    private final double x;
    private final double y;

/**
 * cria um ponto com as suas coordenadas cartesianas
 * @param x coordenada x do ponto
 * @param y coordenada y do ponto
 */
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }


    /**
     * dados 2 pontos e um valor t da equaçao parametrica de uma reta que passa por esses dois pontos
     * devolve outro ponto dessa reta dependendo de t
     * se t = 0 devolve p1 e se t = 1 devolve p2
     * @param p1 um dos pontos da reta
     * @param p2 o outro ponto da reta
     * @param t variavel da equaçao parametrica
     */
    Point(Point p1, Point p2, double t){
        double x = p1.x + t* (p2.x-p1.x);
        double y = p1.y + t* (p2.y-p1.y);
        this.x = x;
        this.y = y;
    }


    /**
     * @return x
     */
    public double x(){
        return x;
    }

    /**
     * @return y
     */
    public double y(){
        return y;
    }

    /**
     * @return x arredondado para inteiro
     */
    public int X(){
        return (int) Math.round(x());
    }
    
    /**
     * @return y arredondado para inteiro
     */
    public int Y(){
        return (int) Math.round(y());
    }

    /**
     * verifica se 2 pontos sao iguais
     * @param that ponto a comparar
     * @return true se forem iguais, senao false
     */
    public boolean equals(Point that){
        if (tolEquals(this.x, that.x) && tolEquals(this.y, that.y))
            return true;
        return false;
    }

    /**
     * calcula a distancia entre o proprio ponto e outro ponto
     * @param that outro ponto
     * @return a distancia entre os pontos
     */
    public double distancia(Point that){
        return Math.sqrt((this.x - that.x) * (this.x - that.x) + (this.y - that.y) * (this.y - that.y));
    }

    /**
     * cria outro ponto com as coordenadas do this alteradas por dx e dy
     * @param dx diferença da posicao no eixo do x
     * @param dy diferença da posicao no eixo do y
     * @return ponto com as coordenadas alteradas
     */
    public Point translacao(double dx, double dy){
        Point out = new Point(this.x+dx, this.y+dy);
        return out;
    }

    public Point sum(Point that){
        return new Point(this.x+that.x, this.y+that.y);
    }

    /**
     * verifica se 3 pontos sao colineares
     * @param a ponto a
     * @param b ponto b
     * @param c ponto c
     * @return true se forem colineares, senao false
     */
    public static boolean colinear(Point a, Point b, Point c){
        Point AB,BC;
        AB = a.vetor(b);
        BC = b.vetor(c);
        if(tol(vetorPE(AB, BC)) == 0)    
            return true;
        return false;
    }

    public Point flipSign(){
        return new Point(-x, -y);
    }


    /**
     * scales a point from 0,0
     * @param s
     * @return
     */
    public Point scale(double s){
        return new Point(this.x*s, this.y*s);
    }

    /**
     * returns new point rotated arround 0,0
     * @param deg rotation in degrees
     * @return
     */
    public Point rotate(double deg){
        double rad = Math.toRadians(deg);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        return new Point(x*cos - y*sin, x*sin + y*cos);
    }

    /**
     * calcula o vetor que transfora o recetor no ponto dado
     * @param that ponto resultante de aplicar o vetor devolvido ao recetor
     * @return array com os valores do vetor: dx na posicao 0 e dy na posicao 1 
     */
    public Point vetor(Point that){
        return new Point(that.x - this.x, that.y - this.y);
    }

    /**
     * dados 2 vetores calcula o produto interno entre eles
     * @param v1 um dos vetores no formato double[] em que dx esta na posicao 0 e dy na posicao 1
     * @param v2 o outro vetore no formato double[] em que dx esta na posicao 0 e dy na posicao 1
     * @return o produto interno dos vetores dados
     */
    public static double vetorPI(Point v1, Point v2){
        return v1.x*v2.x + v1.y*v2.y;
    }

    /**
     * dados 2 vetores calcula o produto externo entre eles
     * @param v1 um dos vetores no formato double[] em que dx esta na posicao 0 e dy na posicao 1
     * @param v2 o outro vetore no formato double[] em que dx esta na posicao 0 e dy na posicao 1
     * @return o produto externo dos vetores dados
     */
    public static double vetorPE(Point v1, Point v2){
        return v1.x*v2.y - v1.y*v2.x;
    }

    @Override
    public String toString() {
        return String.format("(%.2f,%.2f)", x, y);
    }
}
