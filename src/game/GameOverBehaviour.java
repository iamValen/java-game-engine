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

    private long timer;

    /**
     * Construtor
     */
    public GameOverBehaviour() {
        message1 = ObjectCreator.screenMessage( 720, 350, "Game Over", 200, Color.RED);
        message2 = ObjectCreator.screenMessage(720, 550, "press R to play again", 50, Color.cyan);
    }

    @Override
    public void oninit() {
        engine.addDisabled(message1);
        engine.addDisabled(message2);
        timer = System.currentTimeMillis();
    }

    @Override
    public void onUpdate(double dT) {
        long now = System.currentTimeMillis();
        if(engine.isDisabled(message1) && now - timer > 400){
            engine.enable(message1);
        }
        if(engine.isDisabled(message2) && now - timer > 1000){
            engine.enable(message2);
        }
        if(InputManager.isKeyDown(KeyEvent.VK_R))
            Loader.loadLevel(1,1, true);
    }
}
