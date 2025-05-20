package shapes;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import interfaces.IShape;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ScoreShape implements IShape {

    public ScoreShape(PlayerBehaviour own){
        owner = own;
    }

    private PlayerBehaviour owner;

    private final GameEngine engine = GameEngine.getInstance();

    int score;
    Color color = Color.WHITE;

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);

        Font oldFont = g.getFont();
        Font newFont = oldFont.deriveFont(Font.BOLD, 36f);
        g.setFont(newFont);

        score = owner.getScore();
        String scoreStr = Long.toString(score);
        int textWidth = g.getFontMetrics().stringWidth(scoreStr);

        g.drawString(scoreStr, x - textWidth, y);  // Alinha à direita

        g.setFont(oldFont);
    }
}
