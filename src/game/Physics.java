package game;

import geometry.*;
import interfaces.IGameObject;

/**
 * Física básica: aplica gravidade, atrito, 
 * e corrige posição após colisões.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class Physics {

    private Point speed;
    private Point accel;
    private final Point gravity = new Point(0, 160);

    private int layerSpeed;
    private double rotationSpeed;
    private double scaleSpeed;

    private boolean isGrounded;

    /** 
     * Define se está no chão 
     * 
     * @param bool Verdadeiro se estiver no chão
     */
    public void setIsGrounded(boolean bool){
        isGrounded = bool;
    }

    /** 
     * Indica se está no chão 
     * 
     * @return true se estiver no chão
     */
    public boolean isGrounded(){
        return isGrounded;
    }

    /** 
     * Construtor: accel com gravidade, speed a zero 
     */
    public Physics(){
        accel = gravity;
        speed = new Point(0, 0);
        isGrounded = false;
    }

    /** 
     * Atualiza velocidade aplicando física
     * 
     * @param dT Tempo desde o último frame
     */
    public void update(double dT){
        speed = speed.sum(accel.scale(dT));
        speed = speed.scale(Math.pow(0.85 , dT / 0.016666));
        accel = gravity;
    }

    /** 
     * Define nova aceleração 
     * 
     * @param x Componente x
     * @param y Componente y
     */
    public void setAccel(double x, double y){
        accel = new Point(x, y);
    }

    /** 
     * Soma vetorialmente à aceleração 
     * 
     * @param x Incremento x
     * @param y Incremento y
     */
    public void sumAccel(double x, double y){
        accel = accel.sum(new Point(x, y));
    }

    /** 
     * Define nova velocidade 
     * 
     * @param x Componente x
     * @param y Componente y
     */
    public void setSpeed(double x, double y){
        speed = new Point(x, y);
    }

    /** 
     * Define layerSpeed 
     * 
     * @param layerSpeed Valor novo
     */
    public void setLayerSpeed(int layerSpeed){
        this.layerSpeed = layerSpeed;
    }

    /** 
     * Ajusta posição para tocar o chão 
     * 
     * @param GOm Objeto móvel
     * @param GOp Chão
     */
    public static void snapToFloor(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().figure();
        Figure p = GOp.collider().figure();
        double lowest  = m.maxY();
        double highest = p.minY();
        GOm.transform().move(new Point(0, highest - lowest), 0);
    }

    /** 
     * Ajusta posição para não atravessar teto 
     * 
     * @param GOm Objeto móvel
     * @param GOp Teto
     */
    public static void snapToCeling(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().figure();
        Figure p = GOp.collider().figure();
        double highest = m.minY();
        double lowest  = p.maxY();
        GOm.transform().move(new Point(0, lowest - highest), 0);
    }

    /** 
     * Ajusta posição para não atravessar parede esquerda 
     * 
     * @param GOm Objeto móvel
     * @param GOp Parede à esquerda
     */
    public static void snapToWallOnTheLeft(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().figure();
        Figure p = GOp.collider().figure();
        double leftest  = m.minX();
        double rightest = p.maxX();
        GOm.transform().move(new Point(rightest - leftest, 0), 0);
    }

    /** 
     * Ajusta posição para não atravessar parede direita 
     * 
     * @param GOm Objeto móvel
     * @param GOp Parede à direita
     */
    public static void snapToWallOnTheRight(IGameObject GOm, IGameObject GOp) {
        Figure m = GOm.collider().figure();
        Figure p = GOp.collider().figure();
        double rightest = m.maxX();
        double leftest  = p.minX();
        GOm.transform().move(new Point(leftest - rightest, 0), 0);
    }

    /** 
     * @return Velocidade atual 
     */
    public Point Speed(){
        return speed;
    }

    /** 
     * @return Aceleração atual 
     */
    public Point Accel(){
        return accel;
    }

    /** 
     * @return layerSpeed 
     */
    public int LayerSpeed(){
        return layerSpeed;
    }

    /** 
     * @return rotationSpeed 
     */
    public double RotationSpeed(){
        return rotationSpeed;
    }

    /** 
     * @return scaleSpeed 
     */
    public double ScaleSpeed(){
        return scaleSpeed;
    }
}
