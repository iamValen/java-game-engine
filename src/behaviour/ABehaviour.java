package behaviour;
import java.util.ArrayList;

import engine.GameObject;
import interfaces.IGameObject;

public abstract class ABehaviour implements IBehaviour{

    private boolean needsGO = true; 
    protected GameObject go;

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

    /**
     * its not needed to update the collider here thats done in engine however
     * if the user changes the transform in all other methods they should call
     * this.myGO.collider().onUpdate() before leaving that method
     * it is also recommended to call after changing other objects' transform
     * it is just a sugestion tho
     */
    @Override
    public void onUpdate(double dT){}

    /**
     * this is called by the GameObject when the behaviour is inserted onto it
     * DO NOT CALL IT BEFORE THAT OR A METEOR WILL FALL ON YOU
     * you shouldnt ever call this method anyway
     */
    @Override
    public final void setGO(GameObject GO){
        if(this.needsGO){
            this.go = GO;
            this.needsGO = false;
        }
    }

}