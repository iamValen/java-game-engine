package behaviour;

import interfaces.Observer;
import shapes.HealthShape;

public class HealthHUDBehaviour extends AAABehaviour implements Observer {

    private final int width;
    private final int maxHealth;
    private int health;
    private final HealthShape hs;


    public HealthHUDBehaviour(int maxHealth, int width, HealthShape shape){
        this.maxHealth = maxHealth;
        this.width = width;
        this.hs = shape;
    }

    @Override
    public void update(ObserverInfo info) {
        this.health = info.health;
        
        float healthRatio = health / maxHealth;
        int filledWidth = (int) (healthRatio * width);
        hs.update(filledWidth);
    }
}
