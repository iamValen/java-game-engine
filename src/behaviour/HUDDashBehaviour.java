package behaviour;

import interfaces.Observer;
import shapes.DashShape;

public class HUDDashBehaviour extends AAABehaviour implements Observer {

    private int dashCharges;
    private final int maxDashCharges = 2;
    private final long dashRechargeTime = 3000;
    private long now;
    private long lastDashRechargeTime = -1;

    private final int barWidth;
    private final int barHeight = 15;
    private final int barSpacing = 10;

    private final DashShape ds;

    public HUDDashBehaviour(int width, DashShape shape){
        this.barWidth = width;
        this.ds = shape;
    }

    @Override
    public void update(ObserverInfo info) {
        long now = System.currentTimeMillis();
        int charges = info.i;
        lastDashRechargeTime = info.l;

        float progress = (float)(now - lastDashRechargeTime) / dashRechargeTime;
        progress = Math.max(0f, Math.min(1f, progress));
        int filledWidth = (int)(progress * barWidth);

        // envia tudo ao shape:
        ds.update(charges, lastDashRechargeTime, filledWidth);
    }
}
