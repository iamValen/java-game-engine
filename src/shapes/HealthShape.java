package shapes;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

public class HealthShape implements IShape {

    public HealthShape(PlayerBehaviour pb){
        owner = pb;
    }
    private PlayerBehaviour owner;

    private final GameEngine engine = GameEngine.getInstance();

    private Color color = Color.GREEN;

    private int dashCharges = 2;
    private final int maxDashCharges = 2;
    private final long dashRechargeTime = 3000;
    private long now;
    private long lastDashRechargeTime = -1;

    private final int healthBarHeight = 40;
    private final int dashBarWidth = 100;
    private final int dashBarHeight = 15;
    private final int dashBarSpacing = 10;

    private int health;

    private final int maxHealth = 200;

    public void updateDash() {
        this.dashCharges = owner.getDashCharges();
        this.lastDashRechargeTime = owner.getLastDashRechargeTime();
    }

    public void updateHealth(){
        this.health = owner.health().getHealth();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        updateDash();
        now = System.currentTimeMillis();

        int totalDashWidth = maxDashCharges * dashBarWidth + (maxDashCharges - 1) * dashBarSpacing;

        g.setColor(Color.GRAY);
        g.fillRect(x, y - healthBarHeight / 2, totalDashWidth, healthBarHeight);

        float healthRatio = health / (float) maxHealth;
        int filledHealthWidth = (int) (healthRatio * totalDashWidth);

        g.setColor(color);
        g.fillRect(x, y - healthBarHeight / 2, filledHealthWidth, healthBarHeight);

        for (int i = 0; i < maxDashCharges; i++) {
            int barX = x + i * (dashBarWidth + dashBarSpacing);
            int barY = y + healthBarHeight + dashBarSpacing;

            g.setColor(Color.GRAY);
            g.fillRect(barX, barY, dashBarWidth, dashBarHeight);

            if (i < dashCharges) {
                g.setColor(Color.CYAN);
                g.fillRect(barX, barY, dashBarWidth, dashBarHeight);
            } else if (i == dashCharges && lastDashRechargeTime != -1) {
                float progress = (float) (now - lastDashRechargeTime) / dashRechargeTime;
                progress = Math.min(1f, progress);
                int filledWidth = (int) (progress * dashBarWidth);

                g.setColor(Color.CYAN);
                g.fillRect(barX, barY, filledWidth, dashBarHeight);
            }
        }
    }
}
