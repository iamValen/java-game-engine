package shapes;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import interfaces.IShape;
import interfaces.Observer;

import java.awt.Color;
import java.awt.Graphics;

public class HealthShape implements IShape, Observer {
    private final GameEngine engine = GameEngine.getInstance();

    private int observerType = 0;
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

    // Assumimos vida máxima de 100
    private final int maxHealth = 100;

    @Override
    public int type() {
        return observerType;
    }

    @Override
    public void update(PlayerBehaviour playerB) {
        this.health = playerB.health().getHealth();
        this.dashCharges = playerB.getDashCharges();
        this.lastDashRechargeTime = playerB.getLastDashRechargeTime();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        now = System.currentTimeMillis();

        int totalDashWidth = maxDashCharges * dashBarWidth + (maxDashCharges - 1) * dashBarSpacing;

        g.setColor(Color.GRAY);
        g.fillRect(x, y - healthBarHeight / 2, totalDashWidth, healthBarHeight);

        float healthRatio = Math.max(0f, Math.min(1f, health / (float) maxHealth));
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
