package shapes;
import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

public class BlockShape implements IShape {

    private final int width;
    private final int height;
    private Color color;

    public BlockShape(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(x - this.width / 2, y - this.height / 2, this.width, this.height);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}