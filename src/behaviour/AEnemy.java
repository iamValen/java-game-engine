package behaviour;

public abstract class AEnemy extends AAABehaviour implements IDamage, IPoints {
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
    public abstract void playerInRange();

    //this function needs to be defined so enemies with atacks dont crash the game
    //when the atacks try to give them points
    //an enemy can still define behaviour for recieving points
    @Override
    public void recievePoints(int points){}
}
