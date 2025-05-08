package shapes;
import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

public class BlockShape implements IShape {

    private final int width;
    private final int height;

    public BlockShape(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int size() {
        return 0;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.GREEN);
        g.fillRect(x - this.width / 2, y - this.height / 2, this.width, this.height);
    }

}