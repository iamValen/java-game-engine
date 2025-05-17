package shapes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import behaviour.PlayerBehaviour;

import java.io.IOException;

import interfaces.IShape;
import interfaces.Observer;

public class PlayerShape implements IShape, Observer {

    private Map<Integer, SpriteAnimator> animators;
    private int currentState;

    public PlayerShape() {
        animators = new HashMap<>();

        try {
            BufferedImage idleSheet = ImageIO.read(getClass().getResource("/assets/player.png"));
            BufferedImage walkSheet = ImageIO.read(getClass().getResource("/assets/player.png"));
            BufferedImage jumpSheet = ImageIO.read(getClass().getResource("/assets/player.png"));

            animators.put(0, new SpriteAnimator(idleSheet, 8, 64, 64, 15, 3));
            animators.put(1, new SpriteAnimator(walkSheet, 8, 64, 64, 8, 3));
            animators.put(2, new SpriteAnimator(jumpSheet, 4, 64, 64, 20, 3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setState(int newState) {
        if (animators.containsKey(newState)) {
            currentState = newState;
        }
        else System.out.println("Not a state");
    }

    public void update() {
        SpriteAnimator animator = animators.get(currentState);
        if (animator != null) {
            animator.update();
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        SpriteAnimator animator = animators.get(currentState);
        if (animator != null) {
            BufferedImage frame = animator.getCurrentFrame();
            g.drawImage(frame, x - frame.getWidth() / 2, y - frame.getHeight() / 2 - 25, null);
        }
        System.out.println(currentState);
    }

    @Override
    public void update(PlayerBehaviour b){
        int newState = b.state();
        if(currentState != newState) setState(newState);
    }

    @Override
    public int type() {
        return 2;
    }
}
