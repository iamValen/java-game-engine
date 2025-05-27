package game;

import java.util.ArrayList;

import gameManager.Loader;
import interfaces.IGameObject;

/**
 * Behaviour responsável por carregar um novo nível
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class STBehaviour extends AAABehaviour {

    private final int roomKey;  // Identificador da sala para onde será feita a transição
    private final int posKey;   // Posição inicial dentro da nova sala

    /**
     * Construtor
     * 
     * @param roomKey identificador da sala a ser carregada
     * @param posKey posição do jogador na nova sala
     */
    public STBehaviour(int roomKey, int posKey){
        this.roomKey = roomKey;
        this.posKey = posKey;
    }

    /**
     * Método executado quando ocorre uma colisão
     * Se a colisão for com o jogador, carrega o novo nível correspondente
     * 
     * @param gol lista de objetos com os quais ocorreu colisão
     */
    @Override
    public void onCollision(ArrayList<IGameObject> gol){
        for(IGameObject go : gol)
            if(go.name().equals("Player"))
                Loader.loadLevel(roomKey, posKey, false);
    }
}
