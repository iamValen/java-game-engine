import java.util.ArrayList;
import java.util.HashMap;

public interface IGameEngine {
    
    public ArrayList<GameObject> gameObjects();
    public HashMap<Integer, ArrayList<GameObject>> layers();

    public void generateNextFrame();

    public void addEnabled(IGameObject go); // new
    public void addDisabled(IGameObject go); // new

    public void enable(IGameObject go); // new
    public void disable(IGameObject go); // new

    public boolean isEnabled(IGameObject go); // new
    public boolean isDisabled(IGameObject go); // new

    public ArrayList<IGameObject> enabled(); // new
    public ArrayList<IGameObject> disabled(); // new

    public void add(GameObject go);
    public void destroy(IGameObject go); 
    public void destroyAll(); // new

    public void run(); // new

    public ArrayList<String> checkCollisions();
}
