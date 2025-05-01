package figures;
/**
 * Classe abstrata Figura que representa uma figura geométrica.
 * Define os métodos abstratos que devem ser implementados pelas subclasses
 * para realizar operações como translação, rotação, escala e obtenção do centroide.
 * 
 * @author Alexandre Menino a83974
 * @version 28/03/2025
 */
public abstract class Figure {
    public final int tipoFig;
    /*
     * tipoFig indica o tipo da figura:
     * 0 - Polígono
     * 1 - Círculo
     */

    /**
     * Construtor protegido que inicializa o tipo da figura.
     * 
     * @param tipo inteiro que representa o tipo da figura (0 para Polígono, 1 para Círculo)
     */
    protected Figure(int tipo){
        this.tipoFig = tipo;
    }

    /**
     * Retorna o centroide (ponto central) da figura.
     * 
     * @return o ponto que representa o centroide da figura
     */
    public abstract Point centroid();

    /**
     * Retorna uma nova figura que resulta da aplicação de um fator de escala na figura atual.
     * 
     * @param r fator de escala a ser aplicado
     * @return nova figura escalada
     */
    public abstract Figure scale(double r);

    /**
     * Retorna uma nova figura que resulta da rotação da figura atual por um ângulo dado.
     * 
     * @param r ângulo em graus para rotação (no sentido anti-horário)
     * @return nova figura rotacionada
     */
    public abstract Figure rotate(double r);

    /**
     * Retorna uma representação em String da figura.
     * 
     * @return String que representa a figura
     */
    public abstract String toString();

    /**
     * Retorna uma nova figura que resulta da translação da figura atual pelos deslocamentos dx e dy.
     * 
     * @param dx valor a ser adicionado à coordenada x
     * @param dy valor a ser adicionado à coordenada y
     * @return nova figura transladada
     */
    public abstract Figure translation(double dx, double dy);

    /**
     * Retorna uma nova figura que resulta da translação da figura atual pelo vetor representado pelo ponto p.
     * 
     * @param p ponto que representa o vetor de translação
     * @return nova figura transladada
     */
    public abstract Figure translation(Point p);


    public abstract boolean collision(Figure that);
}
