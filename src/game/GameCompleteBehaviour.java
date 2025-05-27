package game;

import engine.GameEngine;
import engine.InputManager;
import gameManager.Loader;
import gameManager.ObjectCreator;
import interfaces.IGameObject;
import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Classe behaviour do objeto por mostrar a mensagem de Fim de Jogo
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class GameCompleteBehaviour extends AAABehaviour {
    GameEngine engine = GameEngine.getInstance();
    IGameObject message1;
    IGameObject message2;
    IGameObject message3;

    private long timer;

    /**
     * Cosntrutor
     * @param score Score do player
     */
    public GameCompleteBehaviour(int score) {
        message1 = ObjectCreator.screenMessage(720, 350, "GG", 300, Color.ORANGE);
        message2 = ObjectCreator.screenMessage(720, 450, String.format("Score: %d", score), 70, Color.GREEN);
        message3 = ObjectCreator.screenMessage(720, 550, "press R to play again", 50, Color.CYAN);
    }

    @Override
    public void oninit() {
        timer = System.currentTimeMillis();
        engine.addDisabled(message1);
        engine.addDisabled(message2);
        engine.addDisabled(message3);
    }

    @Override
    public void onUpdate(double dT) {
        long now = System.currentTimeMillis();
        if(engine.isDisabled(message1) && now - timer > 200){
            engine.enable(message1);
        }
        if(engine.isDisabled(message2) && now - timer > 700){
            engine.enable(message2);
        }
        if(engine.isDisabled(message3) && now - timer > 1400){
            engine.enable(message3);
        }
        if(InputManager.isKeyDown(KeyEvent.VK_R))
            Loader.loadLevel(1,1, true);
    }
}
