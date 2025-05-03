package behaviour;
import java.util.ArrayList;

import engine.GameEngine;
import engine.GameObject;
import interfaces.IGameObject;

public class EnemyBehaviour1 extends ABehaviour {

    GameObject vision;
    GameObject atackRange;
    //both will have a null behaviour and their position will be controlled by the enemy's position

    public EnemyBehaviour1(GameObject go) {
        
    }

    int state;
    /*
     * 1 = walking right
     * 2 = running right
     * 3 = 1st frame of atacking sequence
     * negative for left
     */


    public void oninit(){}
    /*
     * adds vision and atackRange to game object list
     * we might need a public static reference to the GameEngine to do that
     * which isnt as retarded as you may think
     */

    public void onDestroy(){}
    /*
     * spawn other game objects like health pick ups and increase points
     * destroys vision and atackRange
     */
    
    public void onEnable(){}
    public void onDisable(){}
    /*
     * i dont think the enemies are suposed to be enabled or disabled yet
     * at least until we have a moving screen
     */


    //public void onUpdate(input, colisions){}
    /*
     * changes accelaration based on its ai
     * adds acceleration to speed
     * adds speed to position
     * executes onCollision 
     */

    private void onUpdate(){}
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


    public void onCollision(ArrayList<IGameObject> gol){
        for(IGameObject go : gol){
            if(go.name().equals("Player")){
                System.out.println("enemy collides with player");
                GameEngine.getInstance().disable(go);

            }
        }
    }
    /*
     * verifies colisions of itself with at least: player, enemies, player atacks, solid objects
     * enemies and player cause turn arround
     * solid objects push enemies away from them
     */
}
