package interfaces;
import behaviour.IBehaviour;
import java.awt.Graphics;

/**
 * Interface do GameObject.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public interface IGameObject {
    /**
     * @return the name of the GameObject
     */
    String name();

    /**
     * @return the Transform of the GameObject
     */
    ITransform transform();

    /**
     * @return the Shape of the GameObject 
     */
    IShape shape();

    /**
     * @return the Collider of the GameObject with its centroid at this.transform().position()
     */
    ICollider collider();
    
    /**
     * @return the Behaviour of the GameObject
     */
    IBehaviour behaviour();

    public void render(Graphics g);
}
