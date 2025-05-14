package shapes;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import interfaces.IGameObject;
import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

public class ScoreShape implements IShape {

    Color color = Color.BLACK;
    private final GameEngine engine = GameEngine.getInstance();
    
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);
        long score;
        for(IGameObject go : engine.enabled()){
            if(go.name().equals("Player")){
                score = ((PlayerBehaviour) go.behaviour()).getScore();
                g.drawString(((Long)score).toString(), x, y);
                return;
            }
        }
    }
}
