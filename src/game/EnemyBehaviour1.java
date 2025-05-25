package game;

import engine.GameEngine;
import figures.Point;
import gameManager.ObjectCreator;
import interfaces.IGameObject;
import interfaces.ITransform;
import shapes.EnemyShape;

import java.util.ArrayList;

/**
 * Classe Enemy Behaviour
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class EnemyBehaviour1 extends AEnemy {

    private GameEngine engine = GameEngine.getInstance();
    private int moving = 50;

    private final int patrolRangeLeft;
    private final int patrolRangeRight;

    private final int width;
    private final int height;

    private EnemyShape es;

    private IGameObject vision;
    private final int visionRangeX;
    private final int visionRangeY;
    private boolean seesPlayer;

    /**
     * Construtor
     * 
     * @param damage dano que dá
     * @param movespeed velocidade
     * @param width largura
     * @param height altura
     * @param visionRangeX largura do campo de visão
     * @param visionRangeY altura do campo de visão
     */
    public EnemyBehaviour1(int damage, int movespeed, int width, int height, int visionRangeX, int visionRangeY){
        super(1000, damage);
        this.visionRangeX = visionRangeX;
        this.visionRangeY = visionRangeY;
        this.physics = new Physics();
        moving = movespeed;
        this.width = width;
        this.height = height;
        patrolRangeLeft = 50;
        patrolRangeRight = 800;
    }

    /*
     * Devolve o estado
     */
    public State state(){
        return this.state;
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

        state = State.walk;
        es =(EnemyShape) myGo.shape();
    }

    @Override
    public void onDestroy(){
        givePointsToWhoDeserves();
        engine.destroy(vision);
    }


    @Override
    public void onUpdate(double dT){
        //move vision to correct position
        ITransform t = myGo.transform();

        State newState = state;

        if(physics.isGrounded()){
            physics.setAccel(0, 0);
        }

        if(seesPlayer){
            physics.sumAccel(2*moving, 0);
            newState = State.run;
        }
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

            newState = State.walk;
        }


        physics.update(dT);
        t.move(physics.Speed().scale(dT/0.016666), 0);

        // to move vision
        double x = t.position().x() + t.direction()*(width/2 + visionRangeX/2);
        double y = t.position().y() + height/2 - visionRangeY/2;
        vision.transform().setPosition(new Point(x, y), myGo.transform().layer());
        
        physics.setIsGrounded(false);
        seesPlayer = false;
    
        // sprite animation

        if (newState != state) {
            state = newState;
        }

        es =(EnemyShape) myGo.shape();
        es.update();
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
    

}
