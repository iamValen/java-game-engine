
import static util.util.tol;
/**
 * realiza todas as operacoes sobre circulos
 * @author Alexandre Menino a83974
 * @version 3.0 22/03/2025
 * @inv o circulo nao pertence totalmente ao primeiro quadrante 
 */
public class Circulo extends Figura {
    private final Point centro;
    private final Double raio;

    /**
     * cria um circulo dados um ponto e um raio
     * @param c ponto correspondente ao centro do circulo
     * @param r raio do circulo
     */
    public Circulo(Point c, Double r){
        super(1);
        this.centro = c;
        this.raio = r;
        check(c, r);
    }

    /**
     * cria um circulo dados um array de objetos
     * @param nums array de objetos em que Object[0] e Object[1] sao Integer e representam as coordenadas x e y do centro e Object[2] e Double e e o raio do circulo
     */
    public Circulo(Object[] nums){
        super(1);
        Point c = new Point((Integer) nums[0], (Integer) nums[1]);
        this.centro = c;
        double r = (Double) nums[2];
        this.raio = r;
        check(c, r);
    }

    /**
     * verifica se o circulo esta totalmente no primeiro quadrante
     */
    private static void check(Point c, double r){
        if(tol(c.x() - r) < 0 || tol(c.y() - r) < 0)
            throw new IllegalArgumentException("Circulo:vi");
    }

    public Point centro(){
        return centro;
    }
    public Double raio(){
        return raio;
    }

    /**
     * devolve um novo circulo movido pelas coordenadas dadas
     * @param dx alteracao das coordenadas no eixo x
     * @param dy alteracao das coordenadas no eixo y
     * @return novo circulo
     */
    @Override
    public Circulo translacao(double dx, double dy){
        Point c = this.centro.translacao(dx, dy);
        return new Circulo(c, raio);
    }

    @Override
    public Figura translacao(Point p) {
        Point c = this.centro.sum(p);
        return new Circulo(c, raio);
    }

    /**
     * verifica se o segmento dado interseta o circulo
     * @param s segmentos
     * @return true se o segmento intersetar o circulo, senao false
     */
    public boolean intersetaSegmento(Segmento s){
        Point AB = s.p1().vetor(s.p2());
        Point AC = s.p1().vetor(this.centro);
        double t = Point.vetorPI(AB, AC) / Point.vetorPI(AB, AB);
        if(t <= 0){
            if(s.p1().distancia(this.centro) <= this.raio)
            return true;
        }
        if(t >= 1){
            if(s.p2().distancia(this.centro) <= this.raio)
            return true;
        }
        if(0 < t && t < 1){
            if (new Point(s.p1(), s.p2(), t).distancia(this.centro) <= this.raio)
            return true;
        }
        return false;
    }

    /**
     * verifica se o circulo colide com um circulo dado
     * @param that circulo dado
     * @return true se colidirem,  senao false
     */
    public boolean colisaoCirculo(Circulo that){
        if(that.centro.distancia(this.centro) < that.raio+this.raio)
            return true;
        return false;
    }

    /**
     * verifica se o circulo colide com um Poligono dado
     * @param that circulo dado
     * @return true se colidirem,  senao false
     */
    public boolean colisaoPoligono(Poligono that){
        for(int i = 0; i < that.segmentos().length; i++){
            if(intersetaSegmento(that.segmentos()[i]))
                return true;
        }
        if(that.contemOPonto(this.centro)){
            return true;
        }
        return false;
    }

    @Override
    public Point centroid(){
        return centro;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(raio.toString());

        if(raio.toString().contains(".") || raio.toString().contains(",") ){
            while(sb.charAt(sb.length()-1) == '0')
                sb.deleteCharAt(sb.length()-1);
            
            if(sb.charAt(sb.length()-1) == '.' || sb.charAt(sb.length()-1) == ',')
                sb.deleteCharAt(sb.length()-1);
        }
        return String.format("Circulo: (%d,%d) %s", centro.X(), centro.Y(), sb.toString());
    }
}
