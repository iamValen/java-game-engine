package shapes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class SpriteAnimator {
    private boolean loop, finished = false;
    private BufferedImage[] frames;
    private int currentFrame = 0;
    private int tick = 0;
    private int speed;

    public SpriteAnimator(BufferedImage spriteSheet, int totalFrames, int frameWidth, int frameHeight, int speed, double scale, boolean loop) {
        this.frames = new BufferedImage[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            BufferedImage frame = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            frames[i] = resizeImage(frame, (int)(frameWidth * scale), (int)(frameHeight * scale));
        }
        this.loop = loop;
        this.speed = speed;
    }

    public void update() {
        if(finished) return;

        tick++;
        if (tick >= speed) {
            tick = 0;
            if (!loop && currentFrame == frames.length - 1) {
                finished = true;
            } 
            else {
                currentFrame = (currentFrame + 1) % frames.length;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
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
