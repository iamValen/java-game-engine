package behaviour;
import engine.InputManager;
import interfaces.IGameObject;
import interfaces.ITransform;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
@SuppressWarnings("unused")
public class PlayerBehaviour extends ABehaviour{

    int state;
    Physics physics;
    private static final double SPEED = 200; // pixels por segundo
    private double deltaTime;
    private boolean isGrounded;

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
    }
    /*
     * a bunch of stuff would be initialized with the player i guess
     */
    @Override
    public void onDestroy(){}
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
        ITransform t = myGo.transform();

        if(isGrounded){
            physics.setSpeed(physics.Speed().x(), 0);
            physics.setAccel(physics.Accel().x(), 0);

        }

        if (InputManager.isKeyDown(KeyEvent.VK_A)){
            physics.sumAccel(-130, 0);
        }
        if (InputManager.isKeyDown(KeyEvent.VK_D)){
            physics.sumAccel(130, 0);
        }
        if (InputManager.isKeyDown(KeyEvent.VK_W) && isGrounded){
            physics.sumAccel(0, -3000);
        }
        physics.update(dt);
        t.move(physics.Speed(), 0);

        isGrounded = false;
    }

    @Override
    public void onCollision(ArrayList<IGameObject> gol){
        for(IGameObject go : gol){
            switch (go.name()) {
                case ("floor") -> {
                    isGrounded = true;
                }
                case ("e") -> {}
                default -> {}
            }
        }
        physics.update(deltaTime);
    }
    /*
    * verifies colisions of itself with everything
    * enemies and atacks cause damage taken
    * health pickups cause heal
    * solid objects push player away from them
    */
}
