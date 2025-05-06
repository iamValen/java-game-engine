package behaviour;

import gui.Loader;
import interfaces.IGameObject;
import java.util.ArrayList;

public class STBehaviour extends ABehaviour {

    private final int roomKey;
    private final int posKey;

    public STBehaviour(int roomKey, int posKey){
        this.roomKey = roomKey;
        this.posKey = posKey;
    }

    @Override
    public void onCollision(ArrayList<IGameObject> gol){
        Loader.loadLevel(roomKey, posKey);
    }
}
