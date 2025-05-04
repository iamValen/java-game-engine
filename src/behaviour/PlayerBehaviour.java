package behaviour;
import engine.GameObject;
import engine.InputManager;
import engine.Physics;
import figures.Point;
import interfaces.IGameObject;
import interfaces.ITransform;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
@SuppressWarnings("unused")
public class PlayerBehaviour extends ABehaviour{

    int state;
    Physics physics;
    private static final double SPEED = 200; // pixels por segundo

    /*
    * recieves the game object that created this instance
    * recieves stats except position because thats on transform
    * puts go on the static reference
    */
    public PlayerBehaviour(GameObject go){

    }

    Physics physics(){
        return this.physics;
    }

    @Override
    public void oninit(){}
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
        ITransform t = myGo.transform();
        double dx = 0, dy = 0;

        if (InputManager.isKeyDown(KeyEvent.VK_W)) dy -= 500*dt;
        if (InputManager.isKeyDown(KeyEvent.VK_S)) dy += 500*dt;
        if (InputManager.isKeyDown(KeyEvent.VK_A)) dx -= 500*dt;
        if (InputManager.isKeyDown(KeyEvent.VK_D)) dx += 500*dt;
        System.out.println(dt);
        t.move(new Point(dx, dy), 0);
    }

    @Override
    public void onCollision(ArrayList<IGameObject> gol){}
    /*
    * verifies colisions of itself with everything
    * enemies and atacks cause damage taken
    * health pickups cause heal
    * solid objects push player away from them
    */
}
