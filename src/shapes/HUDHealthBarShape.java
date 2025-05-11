package shapes;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import interfaces.IGameObject;
import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

public class HUDHealthBarShape implements IShape {
    GameEngine engine = GameEngine.getInstance();

    Color color;

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void render(Graphics g, int x, int y) {

        int variable = 0;
        for(IGameObject go : engine.enabled()){
            if(go.name().equals("Player"))
                variable = ((PlayerBehaviour)go.behaviour()).entity().getHealth();
            }
        System.out.println(variable);

        g.setColor(color);
        g.fillRect(x, y - 40/2, variable, 40);
    }
    
}
