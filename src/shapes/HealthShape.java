package shapes;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import interfaces.IGameObject;
import interfaces.IShape;
import interfaces.Observer;

import java.awt.Color;
import java.awt.Graphics;

public class HealthShape implements IShape, Observer {
    GameEngine engine = GameEngine.getInstance();

    Color color = Color.GREEN;
    int health;

    @Override
    public void update(int health) {
        this.health = health;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y - 40/2, health, 40);
        return;
    }
}
