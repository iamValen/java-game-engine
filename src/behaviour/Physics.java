package behaviour;

import figures.*;
import interfaces.IGameObject;

/**
 * Implementação básica de física para GameObjects:
 * integra aceleração em velocidade, aplica atrito e reposiciona
 * após colisões com chão, teto e paredes.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class Physics {
    /** Velocidade atual (px/s) */
    private Point speed;
    /** Aceleração atual (px/s²), inclui gravidade e inputs acumulados */
    private Point accel;
    /** Camada de velocidade (não usada atualmente) */
    private int layerSpeed;
    /** Velocidade de rotação (não usada atualmente) */
    private double rotationSpeed;
    /** Velocidade de escala (não usada atualmente) */
    private double scaleSpeed;

    /** Vetor de gravidade aplicado quando accel é reposto */
    private final Point gravity = new Point(0, 160);

    /**
     * Construtor: inicializa accel com gravidade e velocidade a zero.
     */
    public Physics(){
        accel = gravity;
        speed = new Point(0, 0);
    }

    /**
     * Atualiza a velocidade a cada frame:
     * 1) integra accel em speed (v += a * dt)
     * 2) aplica atrito horizontal
     * 3) repõe accel apenas para gravidade
     * 
     * @param dT tempo em segundos desde o último update
     */
    public void update(double  dT){
        // 1) Integração de aceleração
        this.speed = speed.sum(accel.scale(dT));

        // 2) Atrito horizontal
        this.speed = speed.scale(Math.pow(0.85 , dT/0.016666));

        // 3) Repor aceleração para gravidade
        this.accel = gravity;
    }

    /** @return velocidade atual */
    public Point Speed(){
        return this.speed;
    }

    /** @return aceleração atual */
    public Point Accel(){
        return this.accel;
    }

    /** @return layerSpeed (não usado) */
    public int LayerSpeed(){
        return this.layerSpeed;
    }

    /** @return rotationSpeed (não usado) */
    public double RotationSpeed(){
        return this.rotationSpeed;
    }

    /** @return scaleSpeed (não usado) */
    public double ScaleSpeed(){
        return this.scaleSpeed;
    }

    /**
     * Define nova aceleração, substituindo a anterior.
     * 
     * @param x componente x da aceleração
     * @param y componente y da aceleração
     */
    public void setAccel(double x, double y){
        this.accel = new Point(x, y);
    }

    /**
     * Adiciona vetorialmente ao valor de aceleração atual.
     * Útil para combinar inputs com gravidade.
     * 
     * @param x incremento na componente x
     * @param y incremento na componente y
     */
    public void sumAccel(double x, double y){
        this.accel = this.accel.sum(new Point(x, y));
    }

    /**
     * Define diretamente a velocidade atual, zerando a anterior.
     * 
     * @param x nova velocidade x
     * @param y nova velocidade y
     */
    public void setSpeed(double x, double y){
        this.speed = new Point(x, y);
    }
    
    /** Define layerSpeed (não usado) */
    public void setLayerSpeed(int layerSpeed){
        this.layerSpeed = layerSpeed;
    }

    /**
     * Ajusta a posição de GOm para ficar exatamente sobre o chão GOp,
     * evitando sobreposição vertical.
     * 
     * @param GOm objeto em movimento
     * @param GOp objeto de chão estático
     */
    public static void snapToFloor(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().figure();
        Figure p = GOp.collider().figure();
        double lowest  = m.maxY();
        double highest = p.minY();
        GOm.transform().move(new Point(0, highest - lowest), 0);
    }

    /**
     * Ajusta a posição de GOm para não atravessar o teto GOp.
     * 
     * @param GOm objeto em movimento
     * @param GOp objeto de teto estático
     */
    public static void snapToCeling(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().figure();
        Figure p = GOp.collider().figure();
        double highest = m.minY();
        double lowest  = p.maxY();
        GOm.transform().move(new Point(0, lowest - highest), 0);
    }

    /**
     * Impede que GOm atravesse a parede esquerda GOp,
     * alinhando o seu lado esquerdo ao lado direito da parede.
     * 
     * @param GOm objeto em movimento
     * @param GOp parede estática à esquerda
     */
    public static void snapToWallOnTheLeft(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().figure();
        Figure p = GOp.collider().figure();
        double leftest  = m.minX();
        double rightest = p.maxX();
        GOm.transform().move(new Point(rightest - leftest, 0), 0);
    }

    /**
     * Impede que GOm atravesse a parede direita GOp,
     * alinhando o seu lado direito ao lado esquerdo da parede.
     * 
     * @param GOm objeto em movimento
     * @param GOp parede estática à direita
     */
    public static void snapToWallOnTheRight(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().figure();
        Figure p = GOp.collider().figure();
        double rightest = m.maxX();
        double leftest  = p.minX();
        GOm.transform().move(new Point(leftest - rightest, 0), 0);
    }
}
