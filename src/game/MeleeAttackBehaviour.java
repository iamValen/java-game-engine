package game;

import engine.GameEngine;
import interfaces.IPoints;

/**
 * Behaviour do GameObject de um ataque melee
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class MeleeAttackBehaviour extends AAtack{

    private static final GameEngine engine = GameEngine.getInstance();
    private final Physics physics;
    private long start;
    private final long duration;
    private long now;

    /**
     * Construtor
     * 
     * @param own Pontos para passar ao player 
     * @param damage Dano que causa
     * @param duration Duração do ataque
     * @param physics Fisica do dono ou as prórpias
     */
    public MeleeAttackBehaviour(IPoints own, int damage, long duration, Physics physics) {
        super(own, damage);
        this.damage = damage;
        this.duration = duration;
        this.physics = physics;
    }

    @Override
    public int getDamage(){
        return  damage;
    }

    /**
     * Muda o valor do dano do ataque
     * 
     * @param damage
     */
    public void setDamage(int damage){
        this.damage = damage;
    }

    @Override
    public void oninit() {
        start = System.currentTimeMillis();
    }

    @Override
    public void onEnable() {
        start = System.currentTimeMillis();
    }

    @Override
    public void onUpdate(double dT) {
        now = System.currentTimeMillis();
        if(physics != null)
            myGo.transform().move(physics.Speed().scale(dT / 0.016666), 0);
        if(now - start > duration)
            engine.destroy(myGo);
    }
}
