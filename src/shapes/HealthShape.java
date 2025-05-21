package shapes;

import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

public class HealthShape implements IShape {

    private final int barHeight = 40;
    private final int barWidth;
    private int filledWidth;

    public HealthShape(int width){
        this.barWidth = width;
    }

    public void update(int filledHealthWidth){
        this.filledWidth = filledHealthWidth;
    }

    @Override
    public void render(Graphics g, int x, int y) {

        g.setColor(Color.GRAY);
        g.fillRect(x, y - barHeight / 2, barWidth, barHeight);

        g.setColor(Color.GREEN);
        g.fillRect(x, y - barHeight / 2, filledWidth, barHeight);
    }
}
