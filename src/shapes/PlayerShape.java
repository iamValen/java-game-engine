package shapes;

import interfaces.IShape;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import game.PlayerBehaviour;
import game.State;

/**
 * Representa a forma e animação do jogador
 * Gera o sprite correto conforme o estado e direção do jogador
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class PlayerShape implements IShape {

    private State currentState;
    int direction;
    private Map<State, SpriteAnimator> animators;
    private final PlayerBehaviour owner;

    BufferedImage currentFrame;

    /**
     * Construtor
     * 
     * @param own Behaviour do jogador
     */
    public PlayerShape(PlayerBehaviour own) {
        animators = new HashMap<>();
        owner = own;

        // Carrega os sprites e associa a animadores para cada estado do jogador
        try {
            BufferedImage idleSprite = ImageIO.read(getClass().getResource("/assets/player_idle.png"));
            BufferedImage runSprite = ImageIO.read(getClass().getResource("/assets/player_run.png"));
            BufferedImage jumpUpSprite = ImageIO.read(getClass().getResource("/assets/player_jump.png"));
            BufferedImage dashSprite = ImageIO.read(getClass().getResource("/assets/player_dash.png"));
            BufferedImage attackSprite = ImageIO.read(getClass().getResource("/assets/player_attack.png"));
            BufferedImage hurtSprite = ImageIO.read(getClass().getResource("/assets/player_hurt.png"));

            animators.put(State.idle, new SpriteAnimator(idleSprite, 10, 96, 96, 6, 3, true));
            animators.put(State.run, new SpriteAnimator(runSprite, 16, 96, 96, 2, 3, true));
            animators.put(State.jump, new SpriteAnimator(jumpUpSprite, 4, 96, 96, 8, 3, false));
            animators.put(State.dash, new SpriteAnimator(dashSprite, 6, 96, 96, 2, 3, true));
            animators.put(State.attack, new SpriteAnimator(attackSprite, 3, 96, 96, 5, 3, true));
            animators.put(State.hurt, new SpriteAnimator(hurtSprite, 4, 96, 96, 6, 3, false));

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Atualiza o estado do jogador e reinicia a animação correspondente
     * @param newState Novo estado do owner
     */
    public void setState(State newState) {
        if (animators.containsKey(newState)) {
            currentState = newState;
        }
        else System.out.println("Not a state");

        animators.get(newState).reset();
    }

    /** 
     *  Avança a animação do estado atual
     */
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
            currentFrame = animator.getCurrentFrame();

            int width = currentFrame.getWidth();
            int height = currentFrame.getHeight();

            Graphics2D g2d = (Graphics2D) g.create();

            if (direction == -1) {
                g2d.translate(x + width / 2, y - height / 2 - 50);
                g2d.scale(-1, 1); // espelha horizontalmente
                g2d.drawImage(currentFrame, 0, 0, null);
            } else {
                g2d.drawImage(currentFrame, x - width / 2, y - height / 2 - 50, null);
            }

            g2d.dispose();
        }
    }

    /**
     * Verifica mudanças de estado e direção do jogador
     */
    private void updateState(){
        State newState = owner.state();
        int newDirection = owner.gameObject().transform().direction();
        if(currentState != newState) setState(newState);
        if(direction != newDirection) direction = newDirection;
    }

    /**
     * Retorna o índice do frame atual da animação
     * @return Número do frame 
     */
    public int getCurrentFrame() {
        SpriteAnimator animator = animators.get(currentState);
        return animator.getCurrentFrameInt();
    }
}
