package behaviour;

import engine.GameEngine;
import figures.Point;
import gui.Loader;
import gui.ObjectCreator;
import interfaces.IGameObject;
import interfaces.ITransform;
import java.util.ArrayList;

public class BossBehaviour extends AEnemy {

    GameEngine engine = GameEngine.getInstance();
    private Physics physics;
    private Health health;
    private final int width;
    private final int height;

    private long loopStartedAt = 0;
    private final long totalLoopTime = 5000;

    private IGameObject attack1;
    private final int attack1Width = 300;
    private final int attack1Height = 600;
    private final long attack1Duration = 200;
    private final int attack1Damage = 200;
    private final long attack1Start = 3000;
    private boolean canUseAttack1 = true;

    private IGameObject attack2;
    private final int attack2Width = 800;
    private final int attack2Height = 100;
    private final long attack2Duration = 200;
    private final int attack2Damage = 200;
    private final long attack2Start = 4000;
    private boolean canUseAttack2 = true;




    public BossBehaviour(int damage, int width, int height) {
        super(5000, damage);
        physics = new Physics();
        this.width = width;
        this.height = height;
    }

    @Override
    public void oninit() {
        health = new Health(myGo, 500);
        myGo.transform().setDirection(-1);


        attack1 = ObjectCreator.meleeAtack(this, 0, 0, 0, 0, 1, attack1Damage, attack1Width, attack1Height, attack1Duration, null, "EnemyAttack", true);
        attack2 = ObjectCreator.meleeAtack(this, 0, 0, 0, 0, 1, attack2Damage, attack2Width, attack2Height, attack2Duration, null, "EnemyAttack", true);
    }

    @Override
    public void onDestroy() {
        givePointsToWhoDeserves();
        engine.destroy(attack1);
        engine.destroy(attack2);
        Loader.completeGame();
    }

    @Override
    public void onUpdate(double dT) {
        ITransform t = myGo.transform();

        long now = System.currentTimeMillis();

        if(now - loopStartedAt > totalLoopTime){
            loopStartedAt = now;
            canUseAttack1 = true;
            canUseAttack2 = true;
        }

        long elapsedFromLoopStart = now - loopStartedAt;

        if(elapsedFromLoopStart > attack1Start && canUseAttack1) {
            double x = t.position().x() + t.direction()*(width/2 + attack1Width/2);
            double y = t.position().y() + height/2 - attack1Height/2;
            attack1.transform().setPosition(new Point(x, y), myGo.transform().layer());
            engine.addEnabled(attack1);
            canUseAttack1 = false;
        }

        if(elapsedFromLoopStart > attack2Start && canUseAttack2){
            double x = t.position().x() + t.direction()*(width/2 + attack2Width/2);
            double y = t.position().y() + height/2 - attack2Height/2;
            attack2.transform().setPosition(new Point(x, y), myGo.transform().layer());
            engine.addEnabled(attack2);
            canUseAttack2 = false;
        }

        // o boss salta para avisar que vai atacar
        if(elapsedFromLoopStart > 2000 && elapsedFromLoopStart < 2200 )
            physics.sumAccel(0, -370);

        physics.update(dT);
        t.move(physics.Speed().scale(dT/0.016666), 0);
    }

    @Override
    public void onCollision(ArrayList<IGameObject> gol){
        boolean flag = true;
        for(IGameObject go : gol){
            switch (go.name()) {
            case("playerAttack") ->{
                lastAtackThatConnected = (AAtack) go.behaviour();
                health.takeDamage(go);
            }
            case("floor") -> {
                    if(flag)
                        Physics.snapToFloor(myGo, go);
                    physics.setIsGrounded(true);
                }
                case("celing") ->{
                    if(flag)
                        Physics.snapToCeling(myGo, go);
                    flag = false;
                }
                case("rightWall") -> {
                    if(flag)
                        Physics.snapToWallOnTheRight(myGo, go);
                    flag = false;
                }
                case("leftWall") -> {
                    if(flag)
                        Physics.snapToWallOnTheLeft(myGo, go);
                    flag = false;
                }
            }
        }
    }

    @Override
    public void playerInRange() {} //does nothing
}
