/**
 * Realiza todas as operações sobre círculos.
 * Representa uma figura circular com centro e raio.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class Circulo extends Figura {
    private final Point centro;
    private final Double raio;

    /**
     * Cria um círculo dados um ponto e um raio.
     * 
     * @param c ponto correspondente ao centro do círculo
     * @param r raio do círculo
     */
    public Circulo(Point c, Double r){
        super(1);
        this.centro = c;
        this.raio = r;
    }

    /**
     * Retorna o centro do círculo.
     * 
     * @return o ponto que representa o centro
     */
    public Point centro(){ 
        return centro; 
    }

    /**
     * Retorna o raio do círculo.
     * 
     * @return o valor do raio
     */
    public Double raio(){ 
        return raio; 
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
    public Circulo translacao(double dx, double dy){
        Point c = this.centro.translacao(dx, dy);
        return new Circulo(c, raio);
    }

    /**
     * Retorna um novo círculo obtido através da translação do centro pelo ponto p
     * 
     * @param p ponto que representa o vetor de translação
     * @return novo círculo com o centro transladado e mesmo raio
     */
    @Override
    public Figura translacao(Point p) {
        Point c = this.centro.sum(p);
        return new Circulo(c, raio);
    }

    /**
     * Verifica se o segmento dado interseta o círculo
     * 
     * @param s segmento a ser testado
     * @return true se o segmento intersetar o círculo, senao false
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
     * Verifica se este círculo colide com outro círculo dado
     * 
     * @param that outro círculo a ser testado para colisão
     * @return true se os círculos colidirem, senao false
     */
    public boolean colisaoCirculo(Circulo that){
        if(that.centro.distancia(this.centro) < that.raio + this.raio)
            return true;
        return false;
    }

    /**
     * Verifica se este círculo colide com um polígono dado
     * 
     * @param that polígono a ser testado para colisão
     * @return true se houver colisão entre o círculo e o polígono, senao false
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

    /**
     * Retorna o centro do círculo, que é o seu centro.
     * 
     * @return o ponto centroide do círculo
     */
    @Override
    public Point centroid(){
        return centro;
    }

    /**
     * Retorna uma nova figura (círculo) escalada pelo fator r.
     * 
     * @param r fator de escala a ser aplicado no raio
     * @return novo círculo com o raio escalado
     */
    @Override
    public Figura scale(double r){
        return new Circulo(this.centro, this.raio * r);
    }

    /**
     * Metodo rotate no círculo
     * 
     * @param r ângulo de rotação (não utilizado)
     * @return este mesmo círculo, pois a rotação não o altera
     */
    @Override
    public Figura rotate(double r){
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", centro.toString(), raio);
    }
}
