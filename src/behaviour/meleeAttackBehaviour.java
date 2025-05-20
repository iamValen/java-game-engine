package behaviour;

import engine.GameEngine;
import interfaces.IGameObject;

public class meleeAttackBehaviour extends AAtack{

    private static final GameEngine engine = GameEngine.getInstance();
    private IGameObject go;
    private final Physics physics;
    private long start;
    private final long duration;
    private long now;

    public meleeAttackBehaviour(int damage, long duration, Physics physics) {
        super(null, damage);
        this.damage = damage;
        this.duration = duration;
        this.physics = physics;
    }

    public void setGo(IGameObject other){
        this.go = other;
        owner = (IPoints) other.behaviour();
    }

    public IGameObject getGo(){
        return this.go;
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
