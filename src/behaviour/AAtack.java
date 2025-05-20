package behaviour;

public abstract  class AAtack extends ABehaviour implements IPoints, IDamage {

    protected IPoints owner;
    protected int damage;

    @Override
    public int getDamage() {
        return damage;
    }
    @Override
    public final void recievePoints(int points){
        owner.recievePoints(points);
    }

    public AAtack(IPoints own, int dmg) {
        owner = own;
        damage = dmg;
    }

}
