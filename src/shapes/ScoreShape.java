package shapes;

import engine.GameEngine;
import interfaces.IShape;
import interfaces.Observer;

import java.awt.Color;
import java.awt.Graphics;

import behaviour.PlayerBehaviour;

import java.awt.Font;

public class ScoreShape implements IShape, Observer {

    GameEngine engine = GameEngine.getInstance();

    int score;
    Color color = Color.WHITE;

    @Override
    public int type(){
        return 1;
    }

    @Override
    public void update(PlayerBehaviour playerB) {
        int newScore = playerB.getScore();
        this.score = newScore;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);

        Font oldFont = g.getFont();
        Font newFont = oldFont.deriveFont(Font.BOLD, 36f);
        g.setFont(newFont);

        String scoreStr = Long.toString(score);
        int textWidth = g.getFontMetrics().stringWidth(scoreStr);

        g.drawString(scoreStr, x - textWidth, y);  // Alinha à direita

        g.setFont(oldFont);
    }
}
