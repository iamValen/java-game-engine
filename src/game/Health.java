package game;

import engine.GameEngine;
import interfaces.IDamage;
import interfaces.IGameObject;

/**
 * Classe responsável por tratar funções relacionadas com a vida das Entidades
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class Health {
    private final GameEngine engine = GameEngine.getInstance();

    private final IGameObject go;
    private long now = 0;

    private int health;

    private int iFrames = 250;

    long damageTime = 0;

    public void setIFrames(int newVal){
        iFrames = newVal;
    }

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

    /**
     * Obtém o iFrames
     * 
     * @return valor iFrames
     */
    public int getIFrames(){
        return this.iFrames;
    }

    /**
     * Obtem o tempo desde o último dano
     * 
     * @return tempo desde o último dano
     */
    public long getDamageTime(){
        return damageTime;
    }

    /**
     * Tira vida conforme é atacado
     * 
     * @param take objeto que atacou
     */
    public Boolean takeDamage(IGameObject take){
        now = System.currentTimeMillis();
        if(now - damageTime > iFrames){
            damageTime = System.currentTimeMillis();
            this.health -= ((IDamage)take.behaviour()).getDamage();
            if(this.health <= 0)
                return null;
            
            return true;
        }
        return false;
    }
}
