package shapes;

import interfaces.IShape;

import java.awt.Color;
import java.awt.Graphics;

public class Shape implements IShape {
    @Override
    public void render(Graphics g, int x, int y){}

    @Override
    public void setColor(Color color) {
        // Default implementation does nothing
    }
}
