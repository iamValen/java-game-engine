package behaviour;

import engine.GameEngine;

public class meleeAttackBehaviour extends ABehaviour{

    private static final GameEngine engine = GameEngine.getInstance();
    private final Physics physics;
    private long start;
    private final long duration;
    private long now;

    public meleeAttackBehaviour(long duration, Physics physics) {
        this.duration = duration;
        this.physics = physics;
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
