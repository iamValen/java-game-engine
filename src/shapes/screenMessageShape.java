package shapes;

import interfaces.IShape;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Desenha a mensagem com o tamanho da fonte e a cor que são definidos na criação
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 20/05/2025
 */
public class screenMessageShape implements IShape {

    private final String msg;
    private final int size;
    private final Color color;

    /**
     * Construtor
     * 
     * @param message texto da mensagem
     * @param size    tamanho da fonte
     * @param color   cor do texto
     */
    public screenMessageShape(String message, int size, Color color){
        msg = message;
        this.size = size;
        this.color = color;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);

        Font oldFont = g.getFont();
        Font newFont = oldFont.deriveFont(Font.BOLD, size);
        g.setFont(newFont);

        int textWidth = g.getFontMetrics().stringWidth(msg);

        g.drawString(msg, x - textWidth / 2, y);

        g.setFont(oldFont);
    }
}
