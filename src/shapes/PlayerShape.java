package shapes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import interfaces.IShape;

public class PlayerShape implements IShape {
    private SpriteAnimator animator;

    public PlayerShape() {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResource("/assets/player.png"));
            animator = new SpriteAnimator(spriteSheet, 1, 64, 64, 12);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (animator != null) {
            animator.update();
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        if (animator != null) {
            BufferedImage frame = animator.getCurrentFrame();
            int scale = 3;
            g.drawImage(frame, x - frame.getWidth() * 2, y - frame.getHeight() * 2, frame.getWidth() * scale, frame.getHeight() * scale, null);
        }
    }
}
