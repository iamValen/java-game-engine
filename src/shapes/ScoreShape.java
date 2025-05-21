package shapes;

import interfaces.IShape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ScoreShape implements IShape {

    private int score = 0;
    private Color color = Color.WHITE;

    public ScoreShape(){}

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);

        Font oldFont = g.getFont();
        Font newFont = oldFont.deriveFont(Font.BOLD, 36f);
        g.setFont(newFont);

        String scoreStr = Long.toString(score);
        int textWidth = g.getFontMetrics().stringWidth(scoreStr);

        g.drawString(scoreStr, x - textWidth, y);

        g.setFont(oldFont);
    }

    public void update(int i){
        score = i;
    }
}
