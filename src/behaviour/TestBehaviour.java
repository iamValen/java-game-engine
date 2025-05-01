package behaviour;
import figures.Point;

public class TestBehaviour extends ABehaviour {
    public Point posSpeed;
    public int layerSpeed;
    public double rotationSpeed;
    public double scaleSpeed;

    public TestBehaviour(Point posSpeed, int layerSpeed, double rotationSpeed, double scaleSpeed){
        this.posSpeed = posSpeed;
        this.layerSpeed = layerSpeed;
        this.rotationSpeed = rotationSpeed;
        this.scaleSpeed = scaleSpeed;
    }

    public void onUpdate(){
        myGO.transform().move(this.posSpeed, this.layerSpeed);
        myGO.transform().rotate(this.rotationSpeed);
        myGO.transform().scale(this.scaleSpeed);
        myGO.collider().onUpdate();
    }
}
