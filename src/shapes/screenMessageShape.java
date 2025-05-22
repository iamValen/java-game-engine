package shapes;

import interfaces.IShape;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class screenMessageShape implements IShape {
    private final String msg;
    private final int size;
    private final Color color;

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

        g.drawString(msg, x - textWidth/2, y);

        g.setFont(oldFont);
    }
}
