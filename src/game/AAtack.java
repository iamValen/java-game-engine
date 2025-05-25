package game;

import interfaces.IDamage;
import interfaces.IPoints;

/**
 * Behaviour base para ataques.
 * Aplica dano e redireciona pontos.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public abstract class AAtack extends AAABehaviour implements IPoints, IDamage {

    /** 
     * Dono dos pontos 
     */
    protected IPoints owner;

    /** 
     * Dano causado 
     */
    protected int damage;

    /** 
     * Construtor 
     * 
     * @param own Dono dos pontos
     * @param dmg Valor do dano
     */
    public AAtack(IPoints own, int dmg) {
        owner = own;
        damage = dmg;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public final void recievePoints(int points){
        owner.recievePoints(points);
    }
}
