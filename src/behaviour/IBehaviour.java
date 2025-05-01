package behaviour;
import java.util.ArrayList;

import engine.GameObject;
import interfaces.IGameObject;

public interface IBehaviour {

    void oninit();
    void onEnable();
    void onDisable();
    void onDestroy();
    void onUpdate(double dT);
    void onCollision(ArrayList<IGameObject> gol);
    void setGO(GameObject GO);
}
