package game;

import engine.GameEngine;
import engine.InputManager;
import figures.Point;
import gameManager.Loader;
import gameManager.ObjectCreator;
import gameManager.SoundPlayer;
import interfaces.IGameObject;
import interfaces.IPoints;
import interfaces.ITransform;
import interfaces.Observable;
import interfaces.Observer;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import shapes.PlayerShape;

/**
 * Behaviour do GameObject Player 
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 23/05/2025
 */
public class PlayerBehaviour extends AAABehaviour implements IPoints, Observable{

    private final GameEngine engine = GameEngine.getInstance();
    
    private final ArrayList<Observer> ol = new ArrayList<>();

    private IGameObject healthHUD;
    private IGameObject dashHUD;
    private IGameObject scoreHUD;

    private final int width;
    private final int height;

    private Health health;
    private ITransform t;
    private PlayerShape shape;
    private Physics physics;

    private final int maxHealth = 200;

    private State state;
    private State newState;

    private long now;
    
    private int score = 0;

    private boolean isDashing = false;
    private long jumpStart = 0;
    private long dashStart = 0;
    private boolean isJumping = false;
    private boolean canJump = true;

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

    private boolean playedAttackSound = false;
    private boolean playedDashSound = false;

    private long lastFootstepTime;

    /**
     * Construtor
     * 
     * Recieves the game object that created this instance
     * Recieves stats except position because thats on 
     * Transform puts go on the static reference
     * @param width largura do player
     * @param height altura do player
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

    
    @Override
    public void oninit(){
        physics = new Physics();
        if(health == null){
            health = new Health(myGo, maxHealth);
            health.setIFrames(2500);
        }
        myGo.transform().setDirection(1);
        
        state = State.idle;
        
        SoundPlayer.loadSound("attack", "sounds/attack.wav");
        SoundPlayer.loadSound("dash", "sounds/dash.wav");
        SoundPlayer.loadSound("step", "sounds/step.wav");
        
        notifyHealth();
        
        engine.addEnabled(healthHUD);
        engine.addEnabled(dashHUD);
        engine.addEnabled(scoreHUD);
        
        attack1 = ObjectCreator.meleeAtack(this, 0, 0, 0, 0, 1, attack1Damage, attackWidth, attackHeight, attackDuration, physics, "playerAttack", false);
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
    
    
    @Override
    public void onUpdate(double dt){
        now = System.currentTimeMillis();
        
        t = myGo.transform();
        shape =(PlayerShape) myGo.shape();
        newState = state;
        
        if(physics.isGrounded()){
            physics.setAccel(0, 0);
        }
        
        if(dashCharges >= maxDashCharges)
        lastDashRechargeTime = now;
        else{
            if (now - lastDashRechargeTime >= dashRechargeTime){
                dashCharges++;
                lastDashRechargeTime = now;
            }
            notifyDash();
        }
        
        if(isJumping){
            physics.sumAccel(0, -370);
            newState = State.jump;
        }
        
        if (isGrounded() && !isDashing && !isJumping && now - meleeAttackStart > attackDuration
        && physics.Speed().x() <= 3 && physics.Speed().x() >= -3){ 
            newState = State.idle;
            isDashing = false;
        }
        
        // INPUT
        readAndMove(dt);
        
        physics.update(dt);
        t.move(physics.Speed().scale(dt/0.016666), 0);
        
        
        // sprite animation
        if (newState != state){
            state = newState;
        }
        shape.update();
        
        // sounds
        sounds();
        
        physics.setIsGrounded(false);
    }
    
    /**
     * Responsável por mover o jogador conforme o input do utilizador
     * 
     * @param dt tempo desde a ultima chamada
     */
    private void readAndMove(double dt){
        if(InputManager.isKeyDown(KeyEvent.VK_S) && (now - meleeAttackStart > attackCooldown)){
            meleeAttackStart = System.currentTimeMillis();
            
            double x = t.position().x() + t.direction()*(width/2 + attackWidth/2);
            double y = t.position().y();
            attack1.transform().setPosition(new Point(x, y), myGo.transform().layer());
            
            engine.addEnabled(attack1);
            
            isDashing = false;
            
            newState = State.attack;
            
            
        }
        // run
        if ((InputManager.isKeyDown(KeyEvent.VK_LEFT) || InputManager.isKeyDown(KeyEvent.VK_RIGHT))){
            if (InputManager.isKeyDown(KeyEvent.VK_LEFT)){
                physics.sumAccel(-130, 0);
                t.setDirection(-1);
            }
            else {
                physics.sumAccel(130, 0);
                t.setDirection(1);
            }
            
            if(now - meleeAttackStart > attackDuration) newState = State.run;
        }
        // dash
        if(InputManager.isKeyDown(KeyEvent.VK_A) && !isDashing && dashCharges > 0 && now - dashStart > 600){ // dash logic
            dashStart = System.currentTimeMillis();
            isDashing = true;
            dashCharges--;
            
            if (lastDashRechargeTime == -1){
                lastDashRechargeTime = now;
            }
            
            notifyDash();
            
            newState = State.dash;
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
    }
    
    /**
     * Responsável por reproduzir os sons na altura certa
     */
    private void sounds(){
        if (state == State.run && isGrounded()){
            if (now - lastFootstepTime >= 150){
                SoundPlayer.playLoadedSound("step", 90);
                lastFootstepTime = now;
            }
        }
        if (state == State.dash && !playedDashSound){
            SoundPlayer.playLoadedSound("dash", 92);
            playedDashSound = true;
        }
        if (state == State.attack && !playedAttackSound){
            SoundPlayer.playLoadedSound("attack", 100);
            playedAttackSound = true;
        }
        
        if(state != State.attack){
            playedAttackSound = false;
        }
        if(state != State.dash){
            playedDashSound = false;
        }
    }
    
    @Override
    public void onCollision(ArrayList<IGameObject> gol){
        boolean flag = true;
        now = System.currentTimeMillis();
        for(IGameObject go1 : gol){
            switch (go1.name()){
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
                    state = State.hurt;
                    notifyHealth();
                }
                case("EnemyAttack") ->{
                    health.takeDamage(go1);
                    state = State.hurt;
                    notifyHealth();
                }
                default -> {}
            }
        }
        if(health.getHealth() <= 0){
            Loader.gameOver();
        }
    }
    
    /**
     * Verifica se o jogador está no chão.
     * 
     * @return true se o jogador estiver no chão, false caso contrário.
     */
    public boolean isGrounded(){
        return this.physics.isGrounded();
    }
    
    /**
     * Verifica se o jogador está no ar.
     * 
     * @param isGrounded
     */
    public void setGrounded(boolean isGrounded){
        this.physics.setIsGrounded(isGrounded);
    }
    
    @Override
    public void recievePoints(int points){
        score += points;
        notifyScore();
    }
    
    /**
     * Retora physics
     * 
     * @return Físicas do player
     */
    public Physics physics(){
        return this.physics;
    }

    /**
     * Retorna a referência para health
     * 
     * @return Health do player
     */
    public Health health(){
        return this.health;
    }

    /**
     * Retorna o estado do player
     * 
     * @return Player state
     */
    public State state(){
        return this.state;
    }
    
    /**
     * Retorna o score do player
     * 
     * @return Player score
     */
    public int getScore(){
        return score;
    }

    /**
     * Retorna as recargas de dash do player
     * 
     * @return Dash charges
     */
    public int getDashCharges(){
        return dashCharges;
    }

    /**
     * Retorna o tempo do ultimo dash dado
     * 
     * @return lastDashRechargeTime
     */
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
