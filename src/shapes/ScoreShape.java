package shapes;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import interfaces.IGameObject;
import interfaces.IShape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class ScoreShape implements IShape {

    Color color = Color.BLACK;
    private final GameEngine engine = GameEngine.getInstance();
    
    public void render(Graphics g, int x, int y) {
        g.setColor(color);
    
        // 1) Define uma fonte em negrito de tamanho 24
        Font oldFont = g.getFont();
        Font newFont = oldFont.deriveFont(Font.BOLD, 36f);
        g.setFont(newFont);
    
        long score;
        for (IGameObject go : engine.enabled()) {
            if (go.name().equals("Player")) {
                score = ((PlayerBehaviour) go.behaviour()).getScore();
                g.drawString(Long.toString(score), x, y);
                break;
            }
        }
    
        // 2) (Opcional) repõe a fonte antiga se mais nada for desenhado depois
        g.setFont(oldFont);
    }
}
