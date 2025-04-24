import java.util.ArrayList;

public class PlayerBehaviour implements IBehaviour{
    GameObject go;
    int state;

    public PlayerBehaviour(GameObject go){}
    /*
     * recieves the game object that created this instance
     * recieves stats except position because thats on transform
     * puts go on the static reference
     */

    public void oninit(){}
    /*
     * a bunch of stuff would be initialized with the player i guess
     */

    public void onDestroy(){}
    /*
     * idk probably show game over screen
     */
    
    public void onEnable(){}
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


    public void onCollision(ArrayList<IGameObject> gol){}
    /*
    * verifies colisions of itself with everything
    * enemies and atacks cause damage taken
    * health pickups cause heal
    * solid objects push player away from them
    */
}
