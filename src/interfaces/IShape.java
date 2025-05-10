package interfaces;

import java.awt.Color;
import java.awt.Graphics;

public interface IShape {
    public void render(Graphics g, int x, int y);

    public void setColor(Color color);
}
