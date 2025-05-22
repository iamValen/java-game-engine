package behaviour;

import engine.GameEngine;
import engine.InputManager;
import gui.Loader;
import gui.ObjectCreator;
import interfaces.IGameObject;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class gameCompleteBehaviour extends AAABehaviour {
    GameEngine engine = GameEngine.getInstance();
    IGameObject message1;
    IGameObject message2;
    IGameObject message3;

    public gameCompleteBehaviour(int score) {
        message1 = ObjectCreator.screenMessage(720, 350, "GG", 300, Color.ORANGE);
        message2 = ObjectCreator.screenMessage(720, 450, String.format("Score: %d", score), 70, Color.GREEN);
        message3 = ObjectCreator.screenMessage(720, 550, "press R to play again", 50, Color.CYAN);
    }

    @Override
    public void oninit() {
        engine.addEnabled(message1);
        engine.addEnabled(message2);
        engine.addEnabled(message3);
    }

    @Override
    public void onUpdate(double dT) {
        if(InputManager.isKeyDown(KeyEvent.VK_R))
            Loader.loadLevel(1,1);
    }
}
