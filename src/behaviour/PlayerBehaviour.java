package behaviour;

import engine.GameEngine;
import engine.InputManager;
import figures.Point;
import gui.ObjectCreator;
import interfaces.IGameObject;
import interfaces.ITransform;
import interfaces.Observable;
import interfaces.Observer;
import shapes.HealthShape;
import shapes.ScoreShape;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Comportamento responsável por carregar um novo nível.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class PlayerBehaviour extends ABehaviour implements Observable{

    private final GameEngine engine = GameEngine.getInstance();
    
    private List<Observer> observers = new ArrayList<>();

    private int state;
    private int width;
    private int height;
    private final Physics physics;
    private Health health;

    private double deltaTime;
    private long now;

    private boolean isDashing = false;
    private long jumpStart = 0;
    private long dashStart = 0;
    private boolean stopedJumping = true;

    private IGameObject attack1;
    private final int attackWidth = 160;
    private final int attackHeight = 100;
    private long meleeAttackStart = 0;
    private final long attackDuration = 40;
    private final long attackCooldown = 700;
    private final int attack1Damage = 50;

    private int dashCharges = 2;
    private final int maxDashCharges = 2;
    private final long dashRechargeTime = 3000;
    private long lastDashRechargeTime = -1;

    private int score = 0;

    /*
    * recieves the game object that created this instance
    * recieves stats except position because thats on transform
    * puts go on the static reference
    */
    public PlayerBehaviour(int width, int height){
        physics = new Physics();
        this.width = width;
        this.height = height;
    }

    public Physics physics(){
        return this.physics;
    }

    public Health health(){
        return this.health;
    }

    @Override
    public void oninit(){
        health = new Health(myGo, 100);

        myGo.transform().setDirection(1);

        IGameObject healthHUD = ObjectCreator.healthHUD();
        this.addObserver((HealthShape) healthHUD.shape());

        IGameObject scoreHUD = ObjectCreator.score();
        this.addObserver((ScoreShape) scoreHUD.shape());

        notifyObservers();

        engine.addEnabled(healthHUD);
        engine.addEnabled(scoreHUD);

    }

    @Override
    public void onDestroy(){
        // Player died
        engine.destroy(attack1);
    }

    /*
     * changes accelaration based on input
     * adds acceleration to speed
     * adds speed to position
     * executes onCollision 
     */
    @Override
    public void onUpdate(double dt) {
        this.deltaTime = dt;
        now = System.currentTimeMillis();

        ITransform t = myGo.transform();

        if(physics.isGrounded()){
            physics.setAccel(0, 0);
        }

        if (dashCharges < maxDashCharges && now - lastDashRechargeTime >= dashRechargeTime) {
            dashCharges++;
            lastDashRechargeTime = now;
            notifyObservers();
        }

        if (InputManager.isKeyDown(KeyEvent.VK_LEFT)){
            physics.sumAccel(-130, 0);
            myGo.transform().setDirection(-1);
        }
        if (InputManager.isKeyDown(KeyEvent.VK_RIGHT)){
            physics.sumAccel(130, 0);
            myGo.transform().setDirection(1);
        }
        if (InputManager.isKeyDown(KeyEvent.VK_D) && (physics.isGrounded() || now - jumpStart < 300)){ //jump logic
            if(physics.isGrounded()){
                jumpStart = System.currentTimeMillis();
                stopedJumping = false;
            }
            if(!stopedJumping)
                physics.sumAccel(0, -370);
        }
        else{
            stopedJumping = true;
        }

        if(InputManager.isKeyDown(KeyEvent.VK_A) && !isDashing && dashCharges > 0 && now - dashStart > 600){ // dash logic
            dashStart = System.currentTimeMillis();
            isDashing = true;
            dashCharges--;

            if (lastDashRechargeTime == -1) {
                lastDashRechargeTime = now;
            }
            notifyObservers();
        }
        if(isDashing && now - dashStart < 150){
            myGo.transform().move(new Point(35*(dt/0.016666) * myGo.transform().direction(), 0), 0);
            if(engine.enabled().contains(attack1))
                attack1.transform().move(new Point(35*(dt/0.016666) * myGo.transform().direction(), 0), 0);
        }
        else{
            isDashing = false;
        }


        if(InputManager.isKeyDown(KeyEvent.VK_S) && (now - meleeAttackStart > attackCooldown)){

            meleeAttackStart = System.currentTimeMillis();

            double x = t.position().x() + t.direction()*(width/2 + attackWidth/2);
            double y = t.position().y();

            attack1 = ObjectCreator.meleeAtack(x, y, t.layer(), t.angle(), t.scale(), attack1Damage, attackWidth, attackHeight, attackDuration, physics, "playerAttack");
            ((meleeAttackBehaviour) attack1.behaviour()).setGo(this.myGo);
            engine.addEnabled(attack1);
        }
        

        physics.update(dt);
        t.move(physics.Speed().scale(dt/0.016666), 0);

        physics.setIsGrounded(false);
    }

    @Override
    public void onCollision(ArrayList<IGameObject> gol){
        boolean flag = true;
        now = System.currentTimeMillis();
        for(IGameObject go1 : gol){
            switch (go1.name()) {
                case("floor") -> {
                    if(flag)
                        Physics.snapToFloor(myGo, go1);
                    physics.setIsGrounded(true);
                    flag = false;
                }
                case("celing") ->{
                    if(flag)
                        Physics.snapToCeling(myGo, go1);
                    flag = false;
                }
                case("rightWall") -> {
                    if(flag)
                        Physics.snapToWallOnTheRight(myGo, go1);
                    flag = false;
                }
                case("leftWall") -> {
                    if(flag)
                        Physics.snapToWallOnTheLeft(myGo, go1);
                    flag = false;
                }
                case("Enemy1") -> {
                    health.takeDamage(go1);
                    notifyObservers();
                }
                case("EnemyAttack") ->{
                    health.takeDamage(go1);
                    notifyObservers();
                }
                default -> {}
            }
        }
    }

    /* Métodos usados para testes */
    /**
     * Verifica se o jogador está no chão.
     * @return true se o jogador estiver no chão, false caso contrário.
     */
    public boolean isGrounded(){
        return this.physics.isGrounded();
    }

    /**
     * Verifica se o jogador está no ar.
     * @param isGrounded
     */
    public void setGrounded(boolean isGrounded){
        this.physics.setIsGrounded(isGrounded);
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }
    
    public void notifyObservers(){
        for (Observer observer : observers) {
            if(observer.type() == 0) observer.update(this);
            else if (observer.type() == 1) observer.update(this);
        }
    }

    public int getScore(){
        return score;
    }

    public void addScore(int add){
        score += add;
    }

    public int getDashCharges(){
        return dashCharges;
    }

    public long getLastDashRechargeTime(){
        return lastDashRechargeTime;
    }
}
