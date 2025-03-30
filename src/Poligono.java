import static utili.util.*;

/**
 * Realiza todas as operacoes sobre Poligonos
 * @author Alexandre Menino a83974
 * @version 1.0 22/03/2025
 * @inv invariante e um poligono com pontos seguidos colineares ou com segmentos que se intersetam
 */
public class Poligono extends Figura {
    protected final Point[] pontos;
    protected final Segmento[] segmentos;
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
    protected Poligono(Point[] arr, int tipo){
        super(0);
        this.tipoPol = tipo;
        segmentos = new Segmento[arr.length];
        for(int i = 0; i < arr.length; i++){
            segmentos[i] = new Segmento(arr[i], arr[(i+1)%arr.length]);
        }
        pontos = arr;
        checkPol();
    }

    /**
     * Cria um poligono de N lados dado um array de pontos
     * 
     * @param arr array de Ponto
     */
    public Poligono(Point[] arr){
        this(arr, 0);
    }

    /**
     * Verifica se o poligono tem pelo menos 3 lados, nao ha lados colineares consecutivos
     * e os seus segmentos nao se intersetam
     */
    private void checkPol(){
        if(pontos.length < 3) throw new IllegalArgumentException("Poligono:vi");

        for(int i = 0; i < pontos.length; i++) {
            if(Point.colinear(pontos[i], pontos[(i+1)%pontos.length], pontos[(i+2)%pontos.length]))
                throw new IllegalArgumentException("Poligono:vi");
        }

        for(int i = 0; i < pontos.length; i++){
            for(int j = i+2; j < pontos.length; j++){
                Segmento a = segmentos[i];
                Segmento b = segmentos[j%pontos.length];
                if(a.intersecao(b)){
                    throw new IllegalArgumentException("Poligono:vi");
                }
            }
        }
    }

    public Segmento[] segmentos(){ return segmentos; }

    public Point[] pontos(){ return pontos; }

    /**
     * Verifica se um ponto esta dentro do poligono
     * 
     * @param p ponto dado
     * @return true se estiver dentro, senao false
     */
    public boolean contemOPonto(Point p){
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
    public boolean colisaoCirculo(Circulo that){
        return that.colisaoPoligono(this);
    }

    /**
     * Verifica a colisao do poligono com um poligono dado
     * 
     * @param that poligono dado
     * @return true se colidirem, senao false
     */
    public boolean colisaoPoligono(Poligono that){
        for(int i = 0; i < this.pontos.length; i++){
            if(that.contemOPonto(this.pontos[i]))
                return true;
        }
        for(int j = 0; j < that.pontos.length; j++){
            if(this.contemOPonto(that.pontos[j]))
                return true;
        }
        for(int i = 0; i < this.segmentos.length; i++){
            for(int j = 0; j < that.segmentos.length; j++){
                if(this.segmentos[i].intersecao(that.segmentos[j]))
                    return true;
            }
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
    protected  Point[] translacaoPontos(double dx, double dy){
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
    public Poligono translacao(double dx, double dy){
        Point[] pts = translacaoPontos(dx, dy);
        return new Poligono(pts);
    }

    /** 
     * Retorna um novo círculo obtido através da translação do centro pelo ponto p
     * 
     * @param p
     * @return novo poligono
     */
    @Override
    public Figura translacao(Point p){
        Point[] pts = translacaoPontos(p.x(), p.y());
        return new Poligono(pts);
    }

    /**
     * Retorna o centroid do polígono
     */
    @Override
    public Point centroid(){

        Poligono p = this;

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
    public Figura scale(double s){
        Point[] pts = new Point[pontos.length];

        for(int i = 0; i < pontos.length; i++){
            pts[i] = pontos[i].scale(s);
        }

        return new Poligono(pts);
    }

    /**
     * Rotaciona o polígono em torno da origem por um ângulo r (em radianos).
     * Cada ponto do polígono será rotacionado individualmente usando a transformação de rotação.
     * 
     * @param r ângulo de rotação em radianos (positivo para sentido anti-horário, negativo para horário)
     * @return novo polígono rotacionado
     */
    @Override
    public Figura rotate(double r){
        Point[] pts = new Point[pontos.length];

        for(int i = 0; i < pontos.length; i++){
            pts[i] = pontos[i].rotate(r);
        }

        return new Poligono(pts);
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
