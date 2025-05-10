package shapes;

import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

public class TestShape implements IShape {
    private int size;
    private Color color = Color.BLUE;
    
    public TestShape(int size) {
        this.size = size;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    protected void fuckYouJavaFieldCantBeFinal(){
        size++;
    }

    public int getSize(){
        return size;
    }
    
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(x - size/2, y - size/2, size, size);
    }
}
