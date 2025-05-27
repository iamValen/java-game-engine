package shapes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import game.BossBehaviour;
import game.State;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import interfaces.IShape;

/**
 * Desenha e anima o Boss com base no seu estado atual
 * e aos seus ataques
 * Inclui animações de ataque alto e largo
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class BossShape implements IShape {
    private final BossBehaviour owner;
    private State currentState;
    int direction;

    private Map<State, SpriteAnimator> animators;
    private SpriteAnimator showWideAttack;
    private SpriteAnimator showHighAttack;

    /**
     * Construtor
     * 
     * @param own Behaviour do Boss
     */
    public BossShape(BossBehaviour own) {
        animators = new HashMap<>();
        owner = own;

        try {
            /* Boss */
            BufferedImage idleSprite = ImageIO.read(getClass().getResource("/assets/boss_idle.png"));
            BufferedImage screamSprite = ImageIO.read(getClass().getResource("/assets/boss_scream.png"));
            BufferedImage attackSprite = ImageIO.read(getClass().getResource("/assets/boss_attack.png"));
            BufferedImage deadSprite = ImageIO.read(getClass().getResource("/assets/boss_dead.png"));

            animators.put(State.idle, new SpriteAnimator(idleSprite, 5, 128, 128, 6, 2.5, true));
            animators.put(State.jump, new SpriteAnimator(screamSprite, 4, 128, 128, 7, 2.5, true));
            animators.put(State.attack, new SpriteAnimator(attackSprite, 4, 128, 128, 4, 2.5, false)); // high attack
            animators.put(State.dash, new SpriteAnimator(attackSprite, 4, 128, 128, 4, 2.5, false));  // wide attack
            animators.put(State.dead, new SpriteAnimator(deadSprite, 4, 128, 128, 6, 2.5, false));

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

    /**
     * Altera o estado do Boss
     * 
     * @param newState novo estado
     */
    public void setState(State newState) {
        if (animators.containsKey(newState)) {
            currentState = newState;
        } else {
            System.out.println("Not a state");
        }

        animators.get(newState).reset();

        if (newState == State.attack || newState == State.dash) {
            showWideAttack.reset();
            showHighAttack.reset();
        }
    }

    /**
     * Atualiza o quadro de animação atual
     */
    public void update() {
        SpriteAnimator animator = animators.get(currentState);
        if (animator != null) {
            animator.update();
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        updateState();

        // Boss
        Graphics2D g2d = (Graphics2D) g.create();
        SpriteAnimator mainAnim = animators.get(currentState);
        if (mainAnim != null) {
            BufferedImage frame = mainAnim.getCurrentFrame();
            int w = frame.getWidth(), h = frame.getHeight();
            if (direction == -1) {
                g2d.translate(x + w / 2, y - h / 2 - 70);
                g2d.scale(-1, 1);
                g2d.drawImage(frame, 0, 0, null);
            } else {
                g2d.drawImage(frame, x - w / 2, y - h / 2 - 70, null);
            }
        }
        g2d.dispose();

        // high attack
        if (currentState == State.attack) {
            showHighAttack.update();
            BufferedImage attack = showHighAttack.getCurrentFrame();
            int ex = x - attack.getWidth() / 2;
            int ey = y - attack.getHeight() / 2 - 50;
            g.drawImage(attack, ex - 170, ey - 100, null);
        }

        // wide attack
        if (currentState == State.dash) {
            showWideAttack.update();
            BufferedImage attack = showWideAttack.getCurrentFrame();
            int ex = x - attack.getWidth() / 2;
            int ey = y - attack.getHeight() / 2 - 50;
            g.drawImage(attack, ex - 400, ey - 220, null);
        }
    }

    /**
     * Atualiza o estado e direção do Boss
     */
    private void updateState() {
        State newState = owner.state();
        int newDirection = owner.gameObject().transform().direction();
        if (currentState != newState) setState(newState);
        if (direction != newDirection) direction = newDirection;
    }
}
