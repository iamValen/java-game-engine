package behaviour;

import engine.GameEngine;
import interfaces.IGameObject;

/**
 * Representa uma entidade do jogo
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class Health {
    private final IGameObject go;
    private final GameEngine engine = GameEngine.getInstance();
    private long now = 0;

    private int health;

    long damageTime = 0;

    /**
     * Constrói uma nova entidade associada a um GameObject e com vida inicial.
     * 
     * @param go      GameObject que representa visualmente esta entidade
     * @param health  Valor inicial de pontos de vida
     */
    public Health(IGameObject go, int health){
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


    public void takeDamage(IGameObject take){
        now = System.currentTimeMillis();
        if(now - damageTime > 2500){
            damageTime = System.currentTimeMillis();
            this.health -= ((IDamage)take.behaviour()).getDamage();
            if(this.health <= 0)
                engine.destroy(go);
        }
    }


}
