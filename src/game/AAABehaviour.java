package game;

import interfaces.IBehaviour;
import interfaces.IGameObject;
import java.util.ArrayList;

/**
 * Behaviour base com métodos padrão vazios
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public abstract class AAABehaviour implements IBehaviour {

    /** 
     * Objeto de jogo associado 
     */
    protected IGameObject myGo;

    /** 
     * Inicialização 
     */
    @Override
    public void oninit(){}

    /** 
     * Ativado 
     */
    @Override
    public void onEnable(){}

    /** 
     * Desativado 
     */
    @Override
    public void onDisable(){}

    /** 
     * Destruído 
     */
    @Override
    public void onDestroy(){}

    /**
     * Colisão com objetos
     * 
     * @param gol Lista de objetos colididos
     */
    @Override
    public void onCollision(ArrayList<IGameObject> gol){}

    /**
     * Atualização por frame
     * 
     * @param dT Delta de tempo
     */
    @Override
    public void onUpdate(double dT){}

    /**
     * Define o objeto de jogo, se ainda não estiver definido
     * 
     * @param GO Objeto de jogo
     */
    @Override
    public final void IGameObject(IGameObject GO){
        if(this.myGo == null){
            this.myGo = GO;
        }
    }

    /**
     * Retorna o objeto de jogo
     * 
     * @return Objeto de jogo
     */
    @Override
    public IGameObject gameObject() {
        return myGo;
    }
}
