package behaviour;

public class ObserverInfo {
    public final int health;
    public final int dashC;
    public final long lastDashRechargeTime;
    public ObserverInfo(int h, int d, int ldrt) {
        this.health = h;
        this.dashC = d;
        this.lastDashRechargeTime = ldrt;
    }
}
