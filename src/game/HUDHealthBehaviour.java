package game;

import interfaces.Observer;
import shapes.HealthShape;

/**
 * Atualiza a barra de vida no HUD com base na vida atual
 * quando é chamado pela classe observada
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class HUDHealthBehaviour extends AAABehaviour implements Observer {

    private final HealthShape hs;
    private final int maxHealth;
    private int health;
    private final int barWidth;

    /** 
     * Construtor 
     * 
     * @param maxHealth Vida máxima
     * @param width Largura da barra
     * @param shape Forma da barra de vida
     */
    public HUDHealthBehaviour(int maxHealth, int width, HealthShape shape){
        this.maxHealth = maxHealth;
        this.barWidth = width;
        this.hs = shape;
    }

    @Override
    public void update(ObserverInfo info) {
        this.health = info.i;
        
        float healthRatio = (float) health / maxHealth;
        int filledWidth = (int) (healthRatio * barWidth);
        hs.update(filledWidth);
    }
}
