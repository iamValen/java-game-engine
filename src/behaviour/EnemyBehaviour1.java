package behaviour;

import engine.GameEngine;
import figures.Point;
import gui.ObjectCreator;
import interfaces.IGameObject;
import interfaces.ITransform;
import java.util.ArrayList;

/**
 * Classe Abstrata ABehaviour
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class EnemyBehaviour1 extends AEnemy {

    private GameEngine engine = GameEngine.getInstance();
    private long now = 0;
    private int moving = 50;

    private Health health;
    private int state;

    private final int patrolRangeLeft;
    private final int patrolRangeRight;

    private final int width;
    private final int height;


    private IGameObject vision;
    private final int visionRangeX;
    private final int visionRangeY;
    private boolean seesPlayer;

    private AAtack attack1;
    private final int attackWidth = 130;
    private final int attackHeight = 100;
    private long meleeAttackStart = 0;
    private final long attackDuration = 200;
    private final long attackCooldown = 700;
    private final int attack1Damage = 50;

    public EnemyBehaviour1(int damage, int movespeed, int width, int height, int visionRangeX, int visionRangeY){
        super(1000, 50);
        this.visionRangeX = visionRangeX;
        this.visionRangeY = visionRangeY;
        this.physics = new Physics();
        moving = movespeed;
        this.width = width;
        this.height = height;
        patrolRangeLeft = 50;
        patrolRangeRight = 800;
    }

    @Override
    public void playerInRange() {
        seesPlayer = true;
    }

    @Override
    public void oninit(){
        myGo.transform().setDirection(1);
        health = new Health(myGo, 100);
        vision = ObjectCreator.EnemyVision(this, visionRangeX, visionRangeY);
        engine.addEnabled(vision);
    }

    @Override
    public void onDestroy(){
        givePointsToWhoDeserves();
        engine.destroy(vision);
    }

    @Override
    public void onEnable(){}
    
    @Override
    public void onDisable(){}


    @Override
    public void onUpdate(double dT){
        //move vision to correct position
        now = System.currentTimeMillis();
        ITransform t = myGo.transform();

        if(physics.isGrounded()){
            physics.setAccel(0, 0);
        }


        if(seesPlayer)
            physics.sumAccel(2*moving, 0);
        else{
            if(t.position().x() < patrolRangeLeft){
                moving = 50;
                t.setDirection(1);
            }
            if(t.position().x() > patrolRangeRight){
                moving = -50;
                t.setDirection(-1);
            }
            physics.sumAccel(moving, 0);
        }






        physics.update(dT);
        t.move(physics.Speed().scale(dT/0.016666), 0);

        // to move vision
        double x = t.position().x() + t.direction()*(width/2 + visionRangeX/2);
        double y = t.position().y() + height/2 - visionRangeY/2;
        vision.transform().setPosition(new Point(x, y), myGo.transform().layer());

        physics.setIsGrounded(false);
        seesPlayer = false;
    }

    // NOTAS
    /*
     * if it is in an atacking sequence
     *     continues the atacking sequence
     *     return
     * else
     *     checks player colision with vision and atackRange
     *     if colides with atack range
     *         start atack sequence
     *         return
     *     else if colides with vision
     *         speeds up towards the player
     * 
     * patrols when the player doesnt colide
     */
     //this logic terrible because the enemy will have no sense of spacing but who cares 

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
    /*
     * verifies colisions of itself with at least: player, enemies, player atacks, solid objects
     * enemies and player cause turn arround
     * solid objects push enemies away from them
     */
}
