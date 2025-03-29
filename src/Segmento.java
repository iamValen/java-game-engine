import static utili.util.*;

/**
 * realiza todas as operacoes sobre segmentos de reta
 * @author Alexandre Menino a83974
 * @version 4.0  22/03/2025
 * @inv 2 pontos nao formam um segmento de reta
 */
public class Segmento {
    private final Point p1;
    private final Point p2;

    /**
     * cria um segmento de reta dados 2 pontos com coordenadas diferentes
     * @param p1 um extremo do segmento de reta
     * @param p2 o outro extremo do segmento de reta
     */
    public Segmento(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
        check(p1, p2);
    }

    /**
     * verifica se os pontos formam um segmento
     */
    private static void check(Point p1, Point p2){
        if(p1.equals(p2))
            throw new IllegalArgumentException("Segmento:vi");
    }

    public Point p1(){
        return p1;
    }
    public Point p2(){
        return p2;
    }

    /**
     * verifica se os segmentos se intersetam
     * @param that segmento dado
     * @return true se os segmentos se intersetarem, senao false
     */
    public boolean intersecao(Segmento that){
        Point AB, CD, AC, AD, CA, CB;
        AB = this.p1.vetor(this.p2);
        CD = that.p1.vetor(that.p2);
        AC = this.p1.vetor(that.p1);
        AD = this.p1.vetor(that.p2);
        CA = that.p1.vetor(this.p1);
        CB = that.p1.vetor(this.p2);

        double s1 = Math.signum(tol(Point.vetorPE(AB, AC)));
        double s2 = Math.signum(tol(Point.vetorPE(AB, AD)));
        double s3 = Math.signum(tol(Point.vetorPE(CD, CA)));
        double s4 = Math.signum(tol(Point.vetorPE(CD, CB)));

        if(s1 != s2 && s3 != s4 && s1 != 0 && s2 != 0 && s3 != 0 && s4 != 0)
            return true;

        return false;
    }
}
