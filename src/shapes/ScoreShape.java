package shapes;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import interfaces.IGameObject;
import interfaces.IShape;
import interfaces.Observer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class ScoreShape implements IShape, Observer {

    private final GameEngine engine = GameEngine.getInstance();

    int score;
    Color color = Color.BLACK;

    @Override
    public void update(int score) {
        this.score = score;
    }

    public void render(Graphics g, int x, int y) {
        g.setColor(color);
    
        Font oldFont = g.getFont();
        Font newFont = oldFont.deriveFont(Font.BOLD, 36f);
        g.setFont(newFont);
        g.drawString(Long.toString(score), x, y);
        long score;
    
        g.setFont(oldFont);
    }
}
