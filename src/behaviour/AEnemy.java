package behaviour;

public abstract class AEnemy extends ABehaviour implements IDamage, IPoints {
    protected int points;
    protected int contactDamage;
    protected AAtack lastAtackThatConnected;

    @Override
    public int getDamage(){
        return contactDamage;
    }
    protected final void givePointsToWhoDeserves(){
        lastAtackThatConnected.recievePoints(points);
    }
    protected AEnemy(int pts, int dmg){
        points = pts;
        contactDamage = dmg;
    }

    @Override
    public final void recievePoints(int points) {}
}
