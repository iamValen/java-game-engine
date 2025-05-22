package behaviour;

import engine.GameEngine;
import engine.InputManager;
import figures.Point;
import gui.Loader;
import gui.ObjectCreator;
import interfaces.IGameObject;
import interfaces.ITransform;
import interfaces.Observable;
import interfaces.Observer;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import shapes.PlayerShape;

/**
 *
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class PlayerBehaviour extends AAABehaviour implements IPoints, Observable{

    private final GameEngine engine = GameEngine.getInstance();
    
    private final ArrayList<Observer> ol = new ArrayList<>();

    IGameObject healthHUD;
    IGameObject dashHUD;
    IGameObject scoreHUD;

    private PlayerState state;

    private final int width;
    private final int height;
    private Physics physics;
    private Health health;
    private final int maxHealth = 200;

    private long now;

    private boolean isDashing = false;
    private long jumpStart = 0;
    private long dashStart = 0;
    private boolean isJumping = false;
    private boolean canJump = false;

    private IGameObject attack1;
    private final int attackWidth = 170;
    private final int attackHeight = 120;
    private long meleeAttackStart = 0;
    private final long attackDuration = 200;
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
        this.width = width;
        this.height = height;

        healthHUD = ObjectCreator.healthHUD(maxHealth, 210);
        dashHUD = ObjectCreator.dashHUD();
        scoreHUD = ObjectCreator.scoreHUD();

        addObserver(((HUDHealthBehaviour) healthHUD.behaviour()));
        addObserver(((HUDDashBehaviour) dashHUD.behaviour()));
        addObserver(((HUDScoreBehaviour) scoreHUD.behaviour()));
    }

    public Physics physics(){
        return this.physics;
    }

    public Health health(){
        return this.health;
    }

    public PlayerState state(){
        return this.state;
    }

    @Override
    public void oninit(){
        physics = new Physics();
        if(health == null){
            health = new Health(myGo, maxHealth);
            health.setIFrames(2500);
        }
        myGo.transform().setDirection(1);

        state = PlayerState.idle;



        notifyHealth();

        engine.addEnabled(healthHUD);
        engine.addEnabled(dashHUD);
        engine.addEnabled(scoreHUD);

        attack1 = ObjectCreator.meleeAtack(this, 0, 0, 0, 0, 1, attack1Damage, attackWidth, attackHeight, attackDuration, physics, "playerAttack");
    }

    @Override
    public void onDestroy(){
        // Player died
        engine.destroy(attack1);
        engine.destroy(healthHUD);
        engine.destroy(dashHUD);
        engine.destroy(scoreHUD);
        System.out.println("player died");
    }

    /*
     * changes accelaration based on input
     * adds acceleration to speed
     * adds speed to position
     * executes onCollision 
     */
    @Override
    public void onUpdate(double dt) {
        now = System.currentTimeMillis();

        ITransform t = myGo.transform();

        PlayerState newState = state;
        int oldDirection = t.direction();

        if(physics.isGrounded()){
            physics.setAccel(0, 0);
        }

        if(dashCharges >= maxDashCharges)
            lastDashRechargeTime = now;
        else{
            if (now - lastDashRechargeTime >= dashRechargeTime) {
                dashCharges++;
                lastDashRechargeTime = now;
            }
            notifyDash();
        }



        // INPUT
        // attack
        if(InputManager.isKeyDown(KeyEvent.VK_S) && (now - meleeAttackStart > attackCooldown)){
            meleeAttackStart = System.currentTimeMillis();

            double x = t.position().x() + t.direction()*(width/2 + attackWidth/2);
            double y = t.position().y();
            attack1.transform().setPosition(new Point(x, y), myGo.transform().layer());

            engine.addEnabled(attack1);

            isDashing = false;

            newState = PlayerState.attack;
        }
        // run
        if ((InputManager.isKeyDown(KeyEvent.VK_LEFT) || InputManager.isKeyDown(KeyEvent.VK_RIGHT))) {
            if (InputManager.isKeyDown(KeyEvent.VK_LEFT)) {
                physics.sumAccel(-130, 0);
                t.setDirection(-1);
            }
            else {
                physics.sumAccel(130, 0);
                t.setDirection(1);
            }

            if(now - meleeAttackStart > attackDuration + 200)newState = PlayerState.run;
        }
        // dash
        if(InputManager.isKeyDown(KeyEvent.VK_A) && !isDashing && dashCharges > 0 && now - dashStart > 600){ // dash logic
            dashStart = System.currentTimeMillis();
            isDashing = true;
            dashCharges--;

            if (lastDashRechargeTime == -1) {
                lastDashRechargeTime = now;
            }

            notifyDash();

            newState = PlayerState.dash;
        }// continues dash
        if(isDashing && now - dashStart < 240){
            myGo.transform().move(new Point(20*(dt/0.016666) * myGo.transform().direction(), 0), 0);
            if(engine.enabled().contains(attack1))
                attack1.transform().move(new Point(20*(dt/0.016666) * myGo.transform().direction(), 0), 0);
            
            notifyDash();
        }
        else{
            isDashing = false;
        }
        // jump
        if(InputManager.isKeyDown(KeyEvent.VK_D)){
            if(physics.isGrounded() && canJump || now - jumpStart < 300 && isJumping){
                isJumping = true;
                canJump = false;
                if(physics.isGrounded()){
                    jumpStart = System.currentTimeMillis();
                }
            }
            else{
                isJumping = false;
            }
        }
        else{
            isJumping = false;
            canJump = true;
        }
        if(isJumping){
            physics.sumAccel(0, -370);
            newState = PlayerState.jump;
        }

        if (isGrounded() && !isDashing && !isJumping && now - meleeAttackStart > attackDuration + 200 
        && physics.Speed().x() <= 3 && physics.Speed().x() >= -3){ // +200 -> intervalo para a animação do ataque acabar
            newState = PlayerState.idle;
            isDashing = false;
        }
        
        physics.update(dt);
        t.move(physics.Speed().scale(dt/0.016666), 0);

        if (newState != state || t.direction() != oldDirection) {
            state = newState;
        }

        physics.setIsGrounded(false);

        // sprite animation
        PlayerShape ps =(PlayerShape) myGo.shape();
        ps.update();
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
                    if(physics.Speed().y() < 0){
                        isJumping = false;
                    }

                }
                case("celing") ->{
                    if(flag){
                        Physics.snapToCeling(myGo, go1);
                    }
                    canJump = false;
                    isJumping = false;
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
                    state = PlayerState.hurt;
                    notifyHealth();
                }
                case("EnemyAttack") ->{
                    health.takeDamage(go1);
                    state = PlayerState.hurt;
                    notifyHealth();
                }
                default -> {}
            }
        }
        if(health.getHealth() <= 0){
            Loader.gameOver();
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


    @Override
    public void recievePoints(int points) {
        score += points;
        notifyScore();
    }

    public int getScore(){
        return score;
    }

    public int getDashCharges(){
        return dashCharges;
    }

    public long getLastDashRechargeTime(){
        return lastDashRechargeTime;
    }

    @Override
    public final void addObserver(Observer observer){
        ol.add(observer);
    }

    @Override
    public void removeObserver(Observer observer){
        ol.remove(observer);
    }

    @Override
    public void notifyHealth(){
        ObserverInfo info = new ObserverInfo(health.getHealth(), 0);
        ol.get(0).update(info);
    }

    @Override
    public void notifyDash(){
        ObserverInfo info = new ObserverInfo(dashCharges, lastDashRechargeTime);
        ol.get(1).update(info);
    }

    @Override
    public void notifyScore(){
        ObserverInfo info = new ObserverInfo(score, 0);
        ol.get(2).update(info);
    }
}
