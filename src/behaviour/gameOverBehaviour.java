package behaviour;

import engine.GameEngine;
import engine.InputManager;
import gui.Loader;
import gui.ObjectCreator;
import interfaces.IGameObject;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class gameOverBehaviour extends AAABehaviour {

    GameEngine engine = GameEngine.getInstance();
    IGameObject message1;
    IGameObject message2;

    public gameOverBehaviour() {
        message1 = ObjectCreator.screenMessage( 720, 350, "Game Over", 200, Color.RED);
        message2 = ObjectCreator.screenMessage(720, 500, "press R to play again", 50, Color.cyan);
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
