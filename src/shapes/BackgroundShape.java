package shapes;

import interfaces.IShape;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundShape implements IShape {

    private BufferedImage originalImage;
    private Image scaledImage;
    private final int targetWidth = 1440;
    private final int targetHeight = 810;

    public BackgroundShape(String imagePath) {
        try {
            originalImage = ImageIO.read(getClass().getResource(imagePath));
            scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("error loading background: " + imagePath);
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        if (scaledImage != null) {
            g.drawImage(scaledImage, x - targetWidth / 2, y - targetHeight / 2, null);
        }
    }
}
