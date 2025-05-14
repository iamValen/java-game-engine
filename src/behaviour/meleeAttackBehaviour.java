package behaviour;

import engine.GameEngine;

public class meleeAttackBehaviour extends ADamagingBehaviour{

    private static final GameEngine engine = GameEngine.getInstance();
    private final Physics physics;
    private long start;
    private final long duration;
    private long now;
    private int damage;

    public meleeAttackBehaviour(int damage, long duration, Physics physics) {
        this.damage = damage;
        this.duration = duration;
        this.physics = physics;
    }

    @Override
    public int getDamage(){
        return  damage;
    }

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
        myGo.transform().move(physics.Speed().scale(dT / 0.016666), 0);
        if(now - start > duration)
            engine.destroy(myGo);
    }
}
