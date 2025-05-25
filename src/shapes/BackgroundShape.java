package shapes;

import interfaces.IShape;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Representa a imagem de fundo escalada para o jogo
 * Pode ser usada qualquer imagem que irá ser escalada para o tamanho target
 * Renderiza a imagem centralizada no ecrã
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class BackgroundShape implements IShape {

    private BufferedImage originalImage;
    private Image scaledImage;
    private final int targetWidth = 1440;
    private final int targetHeight = 810;

    /**
     * Construtor
     * 
     * @param imagePath Pasta onde está a imagem
     */
    public BackgroundShape(String imagePath) {
        // Carrega e redimensiona a imagem de fundo
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
