package behaviour;

import interfaces.IGameObject;
import java.util.ArrayList;

public class VisionBehaviour extends AAABehaviour {

    private final AEnemy owner;

    public VisionBehaviour(AEnemy own){
        owner = own;
    }

    @Override
    public void onCollision(ArrayList<IGameObject> gol){
        for(IGameObject go : gol){
            if(go.name().equals("Player")){
                owner.playerInRange();
            }
        }
    }
}
