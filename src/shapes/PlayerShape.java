package shapes;

import behaviour.PlayerBehaviour;
import behaviour.PlayerState;
import interfaces.IShape;
import interfaces.Observer;

import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class PlayerShape implements IShape, Observer {

    private PlayerState currentState;
    int direction;
    private Map<PlayerState, SpriteAnimator> animators;

    public PlayerShape() {
        animators = new HashMap<>();

        try {
            BufferedImage idleSprite = ImageIO.read(getClass().getResource("/assets/player_idle.png"));
            BufferedImage runSprite = ImageIO.read(getClass().getResource("/assets/player_run.png"));
            BufferedImage jumpSprite = ImageIO.read(getClass().getResource("/assets/player_idle.png"));
            BufferedImage dashSprite = ImageIO.read(getClass().getResource("/assets/player_idle.png"));
            BufferedImage attackSprite = ImageIO.read(getClass().getResource("/assets/player_attack.png"));


            animators.put(PlayerState.idle, new SpriteAnimator(idleSprite, 10, 96, 96, 10, 3));
            animators.put(PlayerState.run, new SpriteAnimator(runSprite, 16, 96, 96, 2, 3));
            animators.put(PlayerState.jump, new SpriteAnimator(jumpSprite, 8, 96, 96, 10, 3));
            animators.put(PlayerState.dash, new SpriteAnimator(dashSprite, 8, 96, 96, 10, 3));
            animators.put(PlayerState.attack, new SpriteAnimator(attackSprite, 7, 96, 96, 4, 3));
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

        int width = frame.getWidth();
        int height = frame.getHeight();

        Graphics2D g2d = (Graphics2D) g.create();

        if (direction == -1) {
            g2d.translate(x + width / 2, y - height / 2 - 25);
            g2d.scale(-1, 1); // espelha horizontalmente
            g2d.drawImage(frame, 0, 0, null);
        } else {
            g2d.drawImage(frame, x - width / 2, y - height / 2 - 25, null);
        }

        g2d.dispose();
    }
    }

    @Override
    public void update(PlayerBehaviour b){
        PlayerState newState = b.state();
        int newDirection = b.myGo().transform().direction();
        if(currentState != newState) setState(newState);
        if(direction != newDirection) direction = newDirection;
    }

    @Override
    public int type() {
        return 2;
    }
}
