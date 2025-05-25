package game;

import engine.GameEngine;
import gameManager.Loader;
import gameManager.ObjectCreator;
import gameManager.SoundPlayer;
import geometry.Point;
import interfaces.IGameObject;
import interfaces.ITransform;
import java.util.ArrayList;
import shapes.BossShape;

/**
 * Classe Boss Behaviour
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class BossBehaviour extends AEnemy {

    GameEngine engine = GameEngine.getInstance();

    private final int width;
    private final int height;

    BossShape bs;

    private long loopStartedAt = 0;
    private final long totalLoopTime = 5000;

    private IGameObject attack1;
    private final int attack1Width = 300;
    private final int attack1Height = 600;
    private final long attack1Duration = 200;
    private final int attack1Damage = 50;
    private final long attack1Start = 3000;
    private boolean canUseAttack1 = true;

    private IGameObject attack2;
    private final int attack2Width = 600;
    private final int attack2Height = 100;
    private final long attack2Duration = 200;
    private final int attack2Damage = 50;
    private final long attack2Start = 4000;
    private boolean canUseAttack2 = true;

    private long attack1TimeStamp = -1;
    private long attack2TimeStamp = -1;

    private boolean hasScreamed = false;

    public BossBehaviour(int damage, int width, int height) {
        super(5000, damage);
        physics = new Physics();
        this.width = width;
        this.height = height;
    }

    public State state(){
        return this.state;
    }

    @Override
    public void oninit(){
        health = new Health(myGo, 500);
        myGo.transform().setDirection(-1);


        attack1 = ObjectCreator.meleeAtack(this, 0, 0, 0, 0, 1, attack1Damage, attack1Width, attack1Height, attack1Duration, null, "EnemyAttack", false);
        attack2 = ObjectCreator.meleeAtack(this, 0, 0, 0, 0, 1, attack2Damage, attack2Width, attack2Height, attack2Duration, null, "EnemyAttack", false);

        state = State.idle;

        bs = (BossShape) myGo.shape();

        SoundPlayer.loadSound("scream", "sounds/boss_scream.wav");
        SoundPlayer.loadSound("boom1", "sounds/boom1.wav");
        SoundPlayer.loadSound("boom2", "sounds/boom2.wav");
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

        State newState = state;

        long now = System.currentTimeMillis();

        if(now - loopStartedAt > totalLoopTime){
            loopStartedAt = now;
            canUseAttack1 = true;
            canUseAttack2 = true;
        }

        long elapsedFromLoopStart = now - loopStartedAt;

        
        // o boss grita para avisar que vai atacar
        if(elapsedFromLoopStart > 2000 && elapsedFromLoopStart < 3000 ){
            newState = State.jump;
            if(!hasScreamed) SoundPlayer.playLoadedSound("scream", 95);
            hasScreamed = true;
        }
        else{
            newState = State.idle;
            hasScreamed = false;
        }

        if(elapsedFromLoopStart > attack1Start && canUseAttack1) {
            double x = t.position().x() + t.direction()*(width/2 + attack1Width/2);
            double y = t.position().y() + height/2 - attack1Height/2;
            attack1.transform().setPosition(new Point(x, y), myGo.transform().layer());
            engine.addEnabled(attack1);
            canUseAttack1 = false;
            
            attack1TimeStamp = now;

            SoundPlayer.playLoadedSound("boom1", 95);

        }

        if(elapsedFromLoopStart > attack2Start && canUseAttack2){
            double x = t.position().x() + t.direction()*(width/2 + attack2Width/2);
            double y = t.position().y() + height/2 - attack2Height/2;
            attack2.transform().setPosition(new Point(x, y), myGo.transform().layer());
            engine.addEnabled(attack2);
            canUseAttack2 = false;

            attack2TimeStamp = now;

            SoundPlayer.playLoadedSound("boom2", 100);
        }

        // coloca e mantém o estado em "attack" com prioridade
        boolean isAttacking1 = attack1TimeStamp != -1 && now - attack1TimeStamp < attack1Duration + 300;
        boolean isAttacking2 = attack2TimeStamp != -1 && now - attack2TimeStamp < attack2Duration + 300;

        if (isAttacking1) {
            newState = State.attack;
        } else if (isAttacking2) {
            newState = State.dash;
        }

        physics.update(dT);
        t.move(physics.Speed().scale(dT/0.016666), 0);

        // sprite animation

        if (newState != state) {
            state = newState;
        }

        bs =(BossShape) myGo.shape();
        bs.update();
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
