package behaviour;

import interfaces.Observer;
import shapes.DashShape;

public class HUDDashBehaviour extends AAABehaviour implements Observer {

    private final DashShape ds;

    private final long dashRechargeTime = 3000;
    private long lastDashRechargeTime = -1;

    private final int barWidth;

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

        ds.update(charges, lastDashRechargeTime, filledWidth);
    }
}
