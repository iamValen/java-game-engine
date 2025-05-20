package behaviour;

import engine.GameObject;
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

    private long now = 0;
    private long lastTurned = System.currentTimeMillis();
    private int moving = 50;

    private Health health;
    private int state;
    private Physics physics;

    private int patrolRangeX;
    private int patrolRangeY;

    private GameObject vision;
    private int visionRangeX;
    private int visionRangeY;
    private int seesPlayer;

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
    }

    @Override
    public void playerInRange() {
        seesPlayer = 1;        
    }

    @Override
    public void oninit(){
        health = new Health(myGo, 100);
        // vision = ObjectCreator.enemyVision(this, visionRangeX, visionRangeY);
    }

    @Override
    public void onDestroy(){
        givePointsToWhoDeserves();
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

        if (now - lastTurned > 2000) {
            moving*=-1;
            t.setDirection(t.direction()*-1);
            lastTurned = System.currentTimeMillis();
        }
        physics.sumAccel(moving, 0);
        







        physics.update(dT);
        t.move(physics.Speed().scale(dT/0.016666), 0);
        physics.setIsGrounded(false);
        seesPlayer = 0;
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
            // if(go.name().equals("playerAttack")){
                // lastAtackThatConnected = (AAtack)go;
                // health.takeDamage(go);
                // if(health.getHealth() == 0){
                //     IGameObject other = ((meleeAttackBehaviour) go.behaviour()).getGo();
                //     if(other.name().equals("Player")){
                //         PlayerBehaviour playerBehaviour = ((PlayerBehaviour) other.behaviour());
                //         playerBehaviour.addScore(500);
                //         playerBehaviour.notifyObservers();
                //     }
                // }
            // }
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
