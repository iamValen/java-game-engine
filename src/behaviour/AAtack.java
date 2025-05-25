package behaviour;

public abstract  class AAtack extends AAABehaviour implements IPoints, IDamage {

    protected IPoints owner;
    protected int damage;

    public AAtack(IPoints own, int dmg) {
        owner = own;
        damage = dmg;
    }

    @Override
    public int getDamage() {
        return damage;
    }
    @Override
    public final void recievePoints(int points){
        owner.recievePoints(points);
    }

    
}
