package behaviour;
import interfaces.IGameObject;
import java.util.ArrayList;

public interface IBehaviour {

    void oninit();
    void onEnable();
    void onDisable();
    void onDestroy();
    void onUpdate(double dT);
    void onCollision(ArrayList<IGameObject> gol);
    void setGO(IGameObject GO);
}
