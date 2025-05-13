package behaviour;

import interfaces.IBehaviour;
import interfaces.IGameObject;
import java.util.ArrayList;

/**
 * Classe Abstrata ABehaviour
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public abstract class ABehaviour implements IBehaviour{
 
    protected IGameObject myGo;

    @Override
    public void oninit(){}

    @Override
    public void onEnable(){}

    @Override
    public void onDisable(){}

    @Override
    public void onDestroy(){}

    @Override
    public void onCollision(ArrayList<IGameObject> gol){}

    @Override
    public void onUpdate(double dT){}

    @Override
    public final void IGameObject(IGameObject GO){
        if(this.myGo == null){
            this.myGo = GO;
        }
    }

    @Override
    public IGameObject gameObject() {
        return myGo;
    }
}