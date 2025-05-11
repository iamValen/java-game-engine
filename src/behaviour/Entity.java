package behaviour;

import engine.GameEngine;
import figures.Point;
import interfaces.IGameObject;
import java.awt.Color;

@SuppressWarnings("unused")
public class Entity {
    int health;
    IGameObject go;
    GameEngine engine = GameEngine.getInstance();

    long atackStart = 0;
    long damageTime = 0;

    Entity(IGameObject go, int health){
        this.go = go;
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void damage(int damage){
        long now = System.currentTimeMillis();
        if(now - damageTime > 2000){
            this.health -= damage;
            // System.out.println("health: " + this.health);
            if(this.health <= 0){
                engine.destroy(go);
            }
            go.shape().setColor(Color.RED);
        }
    }

    public void createAttack(IGameObject attack1, long now){
        if(engine.isDisabled(attack1) && now - atackStart > 400){
            atackStart = System.currentTimeMillis();
            attack1.transform().move(go.transform().position().sum(attack1.transform().position().flipSign()).sum(new Point(go.transform().getDirection()*40, 0)), 0);
            engine.enable(attack1);
        }
    }

    public void disableAttack(IGameObject attack1, long now){
        if(now - atackStart > 60){
            if(engine.isEnabled(attack1))
                engine.disable(attack1);
        }
    }
}
