package game;

import interfaces.IDamage;
import interfaces.IPoints;

/**
 * Inimigo base com dano, pontos e física.
 * Pode ser atacado e repassar pontos.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public abstract class AEnemy extends AAABehaviour implements IDamage, IPoints {

    protected int points;

    protected int contactDamage;

    protected AAtack lastAtackThatConnected;

    protected Physics physics;

    protected Health health;

    protected State state;

    /** 
     * Retorna a física 
     * 
     * @return Física
     */
    public Physics physics(){
        return physics;
    }

    /** 
     * Retorna o dano de contacto 
     * 
     * @return Dano
     */
    @Override
    public int getDamage(){
        return contactDamage;
    }

    /** 
     * Entrega pontos ao último atacante 
     */
    protected final void givePointsToWhoDeserves(){
        lastAtackThatConnected.recievePoints(points);
    }

    /** 
     * Construtor 
     * 
     * @param pts Pontos
     * @param dmg Dano
     */
    protected AEnemy(int pts, int dmg){
        points = pts;
        contactDamage = dmg;
    }

    /** 
     * Ação quando o jogador está por perto 
     */
    public abstract void playerInRange();

    @Override
    public void recievePoints(int points){}
}
