package shapes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import behaviour.BossBehaviour;
import behaviour.State;
import interfaces.IShape;

public class BossShape implements IShape{
    private final BossBehaviour owner;

    private State currentState;
    int direction;
    private Map<State, SpriteAnimator> animators;
    private SpriteAnimator showWideAttack;
    private SpriteAnimator showHighAttack;

    public BossShape(BossBehaviour own){
        animators = new HashMap<>();
        owner = own;

        try {
            /* Boss */
            BufferedImage idleSprite = ImageIO.read(getClass().getResource("/assets/boss_idle.png"));
            BufferedImage screamSprite = ImageIO.read(getClass().getResource("/assets/boss_scream.png"));
            BufferedImage attackSprite = ImageIO.read(getClass().getResource("/assets/boss_attack.png"));
            
            animators.put(State.idle, new SpriteAnimator(idleSprite, 5, 128, 128, 6, 2.5, true));
            animators.put(State.jump, new SpriteAnimator(screamSprite, 4, 128, 128, 7, 2.5, true));
            animators.put(State.attack, new SpriteAnimator(attackSprite, 4, 128, 128, 4, 2.5, false)); // high attack
            animators.put(State.dash, new SpriteAnimator(attackSprite, 4, 128, 128, 4, 2.5, false)); // wide attack

            /* Attack 1 */
            BufferedImage highAttackImage = ImageIO.read(getClass().getResource("/assets/attack.png"));
            showHighAttack = new SpriteAnimator(highAttackImage, 10, 32, 32, 5, 15, false);

            /* Attack 2 */
            BufferedImage wideAttackImage = ImageIO.read(getClass().getResource("/assets/attack.png"));
            showWideAttack = new SpriteAnimator(wideAttackImage, 10, 32, 32, 5, 25, false);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void setState(State newState) {
        if (animators.containsKey(newState)) {
            currentState = newState;
        }
        else System.out.println("Not a state");  

        animators.get(newState).reset();

        if (newState == State.attack || newState == State.dash) {
            showWideAttack.reset();
            showHighAttack.reset();
        }
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

        // Draw Boss
        Graphics2D g2d = (Graphics2D) g.create();
        SpriteAnimator mainAnim = animators.get(currentState);
        if (mainAnim != null) {
            BufferedImage frame = mainAnim.getCurrentFrame();
            int w = frame.getWidth(), h = frame.getHeight();
            if (direction == -1) {
                g2d.translate(x + w/2, y - h/2 - 70);
                g2d.scale(-1, 1);
                g2d.drawImage(frame, 0, 0, null);
            } else {
                g2d.drawImage(frame, x - w/2, y - h/2 - 70, null);
            }
        }
        g2d.dispose();

        // Draw Attacks
        if (currentState == State.attack){ // High attack
            showHighAttack.update();
            BufferedImage attack = showHighAttack.getCurrentFrame();

            int ex = x - attack.getWidth()/2;
            int ey = y - attack.getHeight()/2 - 50;
            g.drawImage(attack, ex - 170, ey - 100, null);
        }
        if (currentState == State.dash){ // Wide attack
            showWideAttack.update();
            BufferedImage attack = showWideAttack.getCurrentFrame();

            // centra o extra em torno de (x,y) do boss
            int ex = x - attack.getWidth()/2;
            int ey = y - attack.getHeight()/2 - 50;
            g.drawImage(attack, ex - 400, ey - 220, null);
        }
    }

    private void updateState(){
        State newState = owner.state();
        int newDirection = owner.gameObject().transform().direction();
        if(currentState != newState) setState(newState);
        if(direction != newDirection) direction = newDirection;
    }
}
