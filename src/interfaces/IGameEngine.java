package interfaces;
import java.util.ArrayList;
import java.util.HashMap;

public interface IGameEngine {
    
    public HashMap<Integer, ArrayList<IGameObject>> layers();
    public ArrayList<IGameObject> enabled();
    public ArrayList<IGameObject> disabled();

    public void generateNextFrame();

    public void addEnabled(IGameObject go);
    public void addDisabled(IGameObject go);

    public void enable(IGameObject go);
    public void disable(IGameObject go);

    public boolean isEnabled(IGameObject go);
    public boolean isDisabled(IGameObject go);


    public void destroy(IGameObject go); 
    public void destroyAll();

    public void run();

    public void checkCollisions();
}
