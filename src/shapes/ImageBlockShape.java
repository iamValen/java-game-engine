package shapes;

import interfaces.IShape;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageBlockShape implements IShape {

    private final int width;
    private final int height;
    private final BufferedImage texture;

    /**
     * @param width     largura em pixels do bloco
     * @param height    altura em pixels do bloco
     */
    public ImageBlockShape(int width, int height) {
        this.width = width;
        this.height = height;
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/assets/block.png"));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        this.texture = image;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        int tileW = texture.getWidth();
        int tileH = texture.getHeight();

        int countX = width / tileW;
        int countY = height / tileH;

        int startX = x - width / 2;
        int startY = y - height / 2;

        for (int ix = 0; ix < countX; ix++) {
            for (int iy = 0; iy < countY; iy++) {
                int dx = startX + ix * tileW;
                int dy = startY + iy * tileH;
                g2d.drawImage(texture, dx, dy, null);
            }
        }

        g2d.dispose();
    }
}
