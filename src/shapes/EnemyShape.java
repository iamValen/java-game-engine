package shapes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import behaviour.EnemyBehaviour1;
import behaviour.State;
import interfaces.IShape;

public class EnemyShape implements IShape{
    private State currentState;
    int direction;
    private Map<State, SpriteAnimator> animators;
    private final EnemyBehaviour1 owner;

    public EnemyShape(EnemyBehaviour1 own){
        animators = new HashMap<>();
        owner = own;

        try {
            BufferedImage walkSprite = ImageIO.read(getClass().getResource("/assets/enemy_walk.png"));
            BufferedImage runSprite = ImageIO.read(getClass().getResource("/assets/enemy_run.png"));

            animators.put(State.walk, new SpriteAnimator(walkSprite, 6, 128, 128, 6, 1.5, true));
            animators.put(State.run, new SpriteAnimator(runSprite, 7, 128, 128, 2, 1.5, true));

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

    }

    public void setState(State newState) {
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

    public void render(Graphics g, int x, int y) {
        updateState();
        SpriteAnimator animator = animators.get(currentState);
        if (animator != null) {
            BufferedImage frame = animator.getCurrentFrame();

            int width = frame.getWidth();
            int height = frame.getHeight();

            Graphics2D g2d = (Graphics2D) g.create();

            if (direction == -1) {
                g2d.translate(x + width / 2, y - height / 2 - 70);
                g2d.scale(-1, 1);
                g2d.drawImage(frame, 0, 0, null);
            } else {
                g2d.drawImage(frame, x - width / 2, y - height / 2 - 70, null);
            }

            g2d.dispose();
        }
    }

    private void updateState(){
        State newState = owner.state();
        int newDirection = owner.gameObject().transform().direction();
        if(currentState != newState) setState(newState);
        if(direction != newDirection) direction = newDirection;
    }
}
