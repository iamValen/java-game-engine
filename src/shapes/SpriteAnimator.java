package shapes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Permite avançar frames com velocidade configurável e suporte para loops e não loops
 * Também redimensiona frames conforme escala passada no construtor
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 20/05/2025
 */
public class SpriteAnimator {

    private boolean loop;
    private boolean finished = false;
    private BufferedImage[] frames;
    private int currentFrame = 0;
    private int tick = 0;
    private int speed;

    /**
     * Construtor
     * 
     * @param spriteSheet  Imagem com todos os frames em linha
     * @param totalFrames  Total de frames na spritesheet
     * @param frameWidth   Largura de cada frame
     * @param frameHeight  Altura de cada frame
     * @param speed        Velocidade (ticks) entre frames
     * @param scale        Escala para redimensionar frames
     * @param loop         Se a animação deve reiniciar após último frame
     */
    public SpriteAnimator(BufferedImage spriteSheet, int totalFrames, int frameWidth, int frameHeight, int speed, double scale, boolean loop) {
        this.frames = new BufferedImage[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            BufferedImage frame = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            frames[i] = resizeImage(frame, (int)(frameWidth * scale), (int)(frameHeight * scale));
        }
        this.loop = loop;
        this.speed = speed;
    }

    /** 
     * Avança a animação de acordo com a velocidade
     */
    public void update() {
        if (finished) return;

        tick++;
        if (tick >= speed) {
            tick = 0;
            if (!loop && currentFrame == frames.length - 1) {
                finished = true;
            } else {
                currentFrame = (currentFrame + 1) % frames.length;
            }
        }
    }

    /**
     * Reinicia a animação para o primeiro frame
     */
    public void reset() {
        this.currentFrame = 0;
        this.tick = 0;
        this.finished = false;
    }

    /** 
     * Retorna o frame atual da animação
     */
    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }

    /**
     *  Retorna o índice do frame atual
     */
    public int getCurrentFrameInt() {
        return currentFrame;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return resized;
    }
}
