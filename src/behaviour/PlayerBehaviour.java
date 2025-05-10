package behaviour;
import engine.GameEngine;
import engine.InputManager;
import figures.Point;
import gui.ObjectCreator;
import interfaces.IGameObject;
import interfaces.ITransform;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
@SuppressWarnings("unused")
public class PlayerBehaviour extends ABehaviour{

    private final GameEngine engine = GameEngine.getInstance();
    private int state;
    private final Physics physics;
    private double deltaTime;
    private long now;
    private boolean isGrounded;
    private long jumpStart = 0;
    private boolean stopedJumping = true;
    private IGameObject attack1;
    private long atackStart = 0;



    /*
    * recieves the game object that created this instance
    * recieves stats except position because thats on transform
    * puts go on the static reference
    */
    public PlayerBehaviour(){
        physics = new Physics();
        isGrounded = false;
    }

    public Physics physics(){
        return this.physics;
    }

    @Override
    public void oninit(){
        attack1 = ObjectCreator.playerAttack1(40, 40);
        engine.addDisabled(attack1);
    }
    /*
     * a bunch of stuff would be initialized with the player i guess
     */
    @Override
    public void onDestroy(){
        engine.destroy(attack1);
    }
    /*
     * idk probably show game over screen
     */
    
    @Override
    public void onEnable(){}
    @Override
    public void onDisable(){}
    /*
     * i dont think the player is suposed to be enabled or disabled
     */


    //public void onUpdate(input, colisions){}
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

        if(isGrounded){
            physics.setAccel(0, 0);

        }


        if (InputManager.isKeyDown(KeyEvent.VK_A)){
            physics.sumAccel(-130, 0);
            myGo.transform().setDirection(-1);
        }
        if (InputManager.isKeyDown(KeyEvent.VK_D)){
            physics.sumAccel(130, 0);
            myGo.transform().setDirection(1);
        }
        if (InputManager.isKeyDown(KeyEvent.VK_W) && (isGrounded || now - jumpStart < 300)){
            if(isGrounded){
                jumpStart = System.currentTimeMillis();
                stopedJumping = false;
            }
            if(!stopedJumping)
                physics.sumAccel(0, -370);
        }
        else{
            stopedJumping = true;
        }
        if(InputManager.isKeyDown(KeyEvent.VK_E)){
            if(engine.isDisabled(attack1) && now - atackStart > 400){
                atackStart = System.currentTimeMillis();
                attack1.transform().move(myGo.transform().position().sum(attack1.transform().position().flipSign()).sum(new Point(myGo.transform().getDirection()*40, 0)), 0);
                engine.enable(attack1);
            }
        }
        if(now - atackStart > 60){
            if(engine.isEnabled(attack1))
                engine.disable(attack1);
        }



        physics.update(dt);
        t.move(physics.Speed(), 0);

        isGrounded = false;
    }

    @Override
    public void onCollision(ArrayList<IGameObject> gol){
        boolean flag = true;
        for(IGameObject go : gol){
            switch (go.name()) {
                case("floor") -> {
                    if(flag)
                        Physics.snapToFloor(myGo, go);
                    isGrounded = true;
                    flag = false;
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
                case("Enemy1") -> {
                    System.out.println("player collides with enemy");
                    engine.disable(myGo);
                }
                default -> {}
            }
        }
        // physics.update(deltaTime);
    }
    /*
    * verifies colisions of itself with everything
    * enemies and atacks cause damage taken
    * health pickups cause heal
    * solid objects push player away from them
    */
}
