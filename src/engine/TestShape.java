package engine;

import java.awt.Graphics;
import java.awt.Color;
import interfaces.IShape;

public class TestShape implements IShape {
    private int size;
    
    public TestShape(int size) {
        this.size = size;
    }
    
    public int getSize(){
        return size;
    }
    
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.BLUE);
        g.fillRect(x - size/2, y - size/2, size, size);
    }
}
