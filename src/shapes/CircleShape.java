package shapes;
import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;


public class CircleShape implements IShape {
    private final int radius;
    private Color color;

    public CircleShape(int R, Color C){
        radius = R;
        color = C;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);
        g.fillOval(x, y, radius, radius);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
