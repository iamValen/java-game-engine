package shapes;

import interfaces.IShape;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Mostra a pontuação na tela
 * A pontuação é atualizada via o método update
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class ScoreShape implements IShape {

    private int score = 0;
    private Color color = Color.WHITE;

    /**
     * Construtor
     */
    public ScoreShape() {}

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

    /**
     * Atualiza o valor do score
     * 
     * @param i novo score
     */
    public void update(int i) {
        score = i;
    }
}
