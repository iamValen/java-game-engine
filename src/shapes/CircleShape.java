package shapes;

import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Desenha um círculo com raio e cor definidos
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class CircleShape implements IShape {
    private final int radius;
    private Color color;

    /**
     * Construtor
     * 
     * @param R raio do círculo
     * @param C cor do círculo
     */
    public CircleShape(int R, Color C){
        radius = R;
        color = C;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);
        g.fillOval(x, y, radius, radius);
    }

    /**
     * Define uma nova cor para o círculo
     * 
     * @param color nova cor
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
