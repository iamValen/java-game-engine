package behaviour;

import engine.GameEngine;
import figures.Point;
import interfaces.IGameObject;
import java.awt.Color;

/**
 * Representa uma entidade do jogo
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class Entity {
    private final IGameObject go;
    private final GameEngine engine = GameEngine.getInstance();

    private int health;

    private long atackStart = 0;
    long damageTime = 0;

    /**
     * Constrói uma nova entidade associada a um GameObject e com vida inicial.
     * 
     * @param go      GameObject que representa visualmente esta entidade
     * @param health  Valor inicial de pontos de vida
     */
    public Entity(IGameObject go, int health){
        this.go     = go;
        this.health = health;
    }

    /**
     * Obtém a vida atual da entidade.
     * 
     * @return pontos de vida restantes
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * Aplica dano ao ponto de vida da entidade, respeitando um cooldown de 2 segundos.
     * Se a vida cair a zero ou abaixo, destrói o GameObject associado.
     * 
     * @param damage  Quantidade de vida a subtrair
     */
    public void damage(int damage){
        long now = System.currentTimeMillis();
        // Só permite nova aplicação de dano se passaram >2s desde o último
        if (now - damageTime > 2000) {
            this.health -= damage;
            System.out.println("health: " + this.health);
            // Se vida esgotada, remove o GameObject
            if (this.health <= 0) {
                engine.destroy(go);
            }
            go.shape().setColor(Color.RED);
            damageTime = now;
        }
    }

    /**
     * Cria (ativa) um objeto de ataque se estiver desativado e cumprido cooldown de 400ms.
     * Posiciona-o em frente à entidade, segundo a sua direção.
     * 
     * @param attack1  GameObject de ataque a ativar
     * @param now      Timestamp atual (ms) para verificar cooldown
     */
    public void createAttack(IGameObject attack1, long now){
        // Só ativa se o ataque estiver desativado e cooldown completo
        if (engine.isDisabled(attack1) && now - atackStart > 400) {
            atackStart = System.currentTimeMillis();
            // Calcula deslocamento: inverte posição local e aplica offset na direção da entidade
            Point offset = go.transform().position()
                              .sum(attack1.transform().position().flipSign())
                              .sum(new Point(go.transform().getDirection() * 40, 0));
            attack1.transform().move(offset, 0);
            engine.enable(attack1);
        }
    }

    /**
     * Desativa o objeto de ataque caso já tenha passado 60ms desde o início do ataque.
     * 
     * @param attack1  GameObject de ataque a desativar
     * @param now      Timestamp atual (ms) para verificar duração do ataque
     */
    public void disableAttack(IGameObject attack1, long now){
        // Se o ataque foi ativado há >60ms, desativa-o
        if (now - atackStart > 60) {
            if (engine.isEnabled(attack1)) {
                engine.disable(attack1);
            }
        }
    }
}
