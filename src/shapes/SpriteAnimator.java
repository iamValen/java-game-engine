package shapes;

import java.awt.image.BufferedImage;

public class SpriteAnimator {
    private BufferedImage[] frames;
    private int currentFrame = 0;
    private int tick = 0;
    private int speed;

    public SpriteAnimator(BufferedImage spriteSheet, int totalFrames, int frameWidth, int frameHeight, int speed) {
        this.frames = new BufferedImage[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        this.speed = speed;
    }

    public void update() {
        tick++;
        if (tick >= speed) {
            tick = 0;
            currentFrame = (currentFrame + 1) % frames.length;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }
}

