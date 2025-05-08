package shapes;

import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

public class TestShape implements IShape {
    private int size;
    
    public TestShape(int size) {
        this.size = size;
    }

    protected void fuckYouJavaFieldCantBeFinal(){
        size++;//just trying to get rid of warnings
    }

    public int getSize(){
        return size;
    }
    
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.BLUE);
        g.fillRect(x - size/2, y - size/2, size, size);
    }
}
