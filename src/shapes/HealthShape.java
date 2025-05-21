package shapes;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

public class HealthShape implements IShape {

    private final int barHeight = 40;
    private final int barWidth;
    private int filledWidth;

    public HealthShape(int width){
        this.barWidth = width;
    }

    private int dashCharges = 2;
    private final int maxDashCharges = 2;
    private final long dashRechargeTime = 3000;
    private long now;
    private long lastDashRechargeTime = -1;

    private final int dashBarWidth = 100;
    private final int dashBarHeight = 15;
    private final int dashBarSpacing = 10;


    

    public void updateDash() {
        this.dashCharges = owner.getDashCharges();
        this.lastDashRechargeTime = owner.getLastDashRechargeTime();
    }

    public void update(int filledHealthWidth){
        this.filledWidth = filledHealthWidth;
    }


    @Override
    public void render(Graphics g, int x, int y) {

        g.setColor(Color.GRAY);
        g.fillRect(x, y - barHeight / 2, barWidth, barHeight);

        g.setColor(Color.GREEN);
        g.fillRect(x, y - barHeight / 2, filledWidth, barHeight);





        updateDash();
        now = System.currentTimeMillis();

        for (int i = 0; i < maxDashCharges; i++) {
            int barX = x + i * (dashBarWidth + dashBarSpacing);
            int barY = y + barHeight + dashBarSpacing;

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
