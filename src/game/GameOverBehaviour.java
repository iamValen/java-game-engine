package game;

import engine.GameEngine;
import engine.InputManager;
import gameManager.Loader;
import gameManager.ObjectCreator;
import interfaces.IGameObject;
import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Classe behaviour do objeto por mostrar a mensagem de Game Over
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class GameOverBehaviour extends AAABehaviour {

    GameEngine engine = GameEngine.getInstance();
    IGameObject message1;
    IGameObject message2;

    /**
     * Construtor
     */
    public GameOverBehaviour() {
        message1 = ObjectCreator.screenMessage( 720, 350, "Game Over", 200, Color.RED);
        message2 = ObjectCreator.screenMessage(720, 550, "press R to play again", 50, Color.cyan);
    }

    @Override
    public void oninit() {
        engine.addEnabled(message1);
        engine.addEnabled(message2);
    }

    @Override
    public void onUpdate(double dT) {
        if(InputManager.isKeyDown(KeyEvent.VK_R))
            Loader.loadLevel(1,1);
    }
}
