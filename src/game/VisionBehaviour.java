package game;

import interfaces.IGameObject;
import java.util.ArrayList;

/**
 * Comportamento que representa a visão de um inimigo.
 * Detecta colisão com o jogador e avisa o inimigo.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class VisionBehaviour extends AAABehaviour {

    private final AEnemy owner;

    /**
     * Construtor
     * 
     * @param own Dono da visão
     */
    public VisionBehaviour(AEnemy own){
        owner = own;
    }

    @Override
    public void onCollision(ArrayList<IGameObject> gol){
        for(IGameObject go : gol){
            if(go.name().equals("Player")){
                owner.playerInRange();
            }
        }
    }
}
