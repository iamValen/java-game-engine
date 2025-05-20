package shapes;

import behaviour.PlayerBehaviour;
import behaviour.PlayerState;
import interfaces.IShape;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class PlayerShape implements IShape {

    private PlayerState currentState;
    int direction;
    private Map<PlayerState, SpriteAnimator> animators;
    private final PlayerBehaviour owner;

    public PlayerShape(PlayerBehaviour own) {
        animators = new HashMap<>();
        owner = own;

        try {
            BufferedImage idleSprite = ImageIO.read(getClass().getResource("/assets/player_idle.png"));
            BufferedImage runSprite = ImageIO.read(getClass().getResource("/assets/player_run.png"));
            BufferedImage jumpUpSprite = ImageIO.read(getClass().getResource("/assets/player_jump.png"));
            BufferedImage dashSprite = ImageIO.read(getClass().getResource("/assets/player_dash.png"));
            BufferedImage attackSprite = ImageIO.read(getClass().getResource("/assets/player_attack.png"));
            BufferedImage hurtSprite = ImageIO.read(getClass().getResource("/assets/player_hurt.png"));

            animators.put(PlayerState.idle, new SpriteAnimator(idleSprite, 10, 96, 96, 6, 3, true));
            animators.put(PlayerState.run, new SpriteAnimator(runSprite, 16, 96, 96, 2, 3, true));
            animators.put(PlayerState.jump, new SpriteAnimator(jumpUpSprite, 4, 96, 96, 8, 3, false));
            animators.put(PlayerState.dash, new SpriteAnimator(dashSprite, 6, 96, 96, 2, 3, true));
            animators.put(PlayerState.attack, new SpriteAnimator(attackSprite, 7, 96, 96, 5, 3, true));
            animators.put(PlayerState.hurt, new SpriteAnimator(hurtSprite, 4, 96, 96, 4, 3, false));

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void setState(PlayerState newState) {
        if (animators.containsKey(newState)) {
            currentState = newState;
        }
        else System.out.println("Not a state");
    }

    public void update(){ 
        SpriteAnimator animator = animators.get(currentState);
        if (animator != null) {
            animator.update();
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        updateState();
        SpriteAnimator animator = animators.get(currentState);
        if (animator != null) {
            BufferedImage frame = animator.getCurrentFrame();

            int width = frame.getWidth();
            int height = frame.getHeight();

            Graphics2D g2d = (Graphics2D) g.create();

            if (direction == -1) {
                g2d.translate(x + width / 2, y - height / 2 - 50);
                g2d.scale(-1, 1); // espelha horizontalmente
                g2d.drawImage(frame, 0, 0, null);
            } else {
                g2d.drawImage(frame, x - width / 2, y - height / 2 - 50, null);
            }

            g2d.dispose();
        }
    }

    private void updateState(){
        PlayerState newState = owner.state();
        int newDirection = owner.gameObject().transform().direction();
        if(currentState != newState) setState(newState);
        if(direction != newDirection) direction = newDirection;
    }
}
