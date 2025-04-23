import java.util.ArrayList;

public interface IBehaviour {
    
    void init();
    void onEnabled();
    void onDisabled();
    void onDestroy();
    void onUpdate(double dT, InputEvent ie);
    void onCollision(ArrayList<IGameObject> gol);
}
