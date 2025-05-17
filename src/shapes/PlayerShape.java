package shapes;

import behaviour.PlayerBehaviour;
import behaviour.PlayerState;
import interfaces.IShape;
import interfaces.Observer;

import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class PlayerShape implements IShape, Observer {

    private PlayerState currentState;
    private Map<PlayerState, SpriteAnimator> animators;


    public PlayerShape() {
        animators = new HashMap<>();

        try {
            BufferedImage idleSprite = ImageIO.read(getClass().getResource("/assets/player.png"));
            BufferedImage walkSprite = ImageIO.read(getClass().getResource("/assets/player.png"));
            BufferedImage jumpSprite = ImageIO.read(getClass().getResource("/assets/player.png"));
            BufferedImage dashSprite = ImageIO.read(getClass().getResource("/assets/player.png"));
            BufferedImage attackSprite = ImageIO.read(getClass().getResource("/assets/player.png"));


            animators.put(PlayerState.idle, new SpriteAnimator(idleSprite, 8, 64, 64, 15, 3));
            animators.put(PlayerState.run, new SpriteAnimator(walkSprite, 8, 64, 64, 8, 3));
            animators.put(PlayerState.jump, new SpriteAnimator(jumpSprite, 8, 64, 64, 20, 3));
            animators.put(PlayerState.dash, new SpriteAnimator(dashSprite, 8, 64, 64, 20, 3));
            animators.put(PlayerState.attack, new SpriteAnimator(attackSprite, 8, 64, 64, 20, 3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setState(PlayerState newState) {
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
        PlayerState newState = b.state();
        if(currentState != newState) setState(newState);
    }

    @Override
    public int type() {
        return 2;
    }
}
