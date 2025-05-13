package behaviour;
import engine.GameEngine;
import figures.Point;
import java.util.ArrayList;
import interfaces.IGameObject;

/**
 * Classe Abstrata ABehaviour
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class EnemyBehaviour1 extends ABehaviour {

    private IGameObject vision;
    private final Point speed = new Point(0, 0);
    private Entity entity;

    public EnemyBehaviour1() {}

    int state;


    @Override
    public void oninit(){
        entity = new Entity(myGo, 100);
    }

    @Override
    public void onDestroy(){}

    @Override
    public void onEnable(){}
    
    @Override
    public void onDisable(){}


    @Override
    public void onUpdate(double dT){

        myGo.transform().move(speed, 0);
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
        for(IGameObject go : gol){
            if(go.name().equals("playerAttack")){
                entity.damage(50);
                entity.damageTime = System.currentTimeMillis();
            }
        }
    }
    /*
     * verifies colisions of itself with at least: player, enemies, player atacks, solid objects
     * enemies and player cause turn arround
     * solid objects push enemies away from them
     */
}
