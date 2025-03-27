public class GameObject implements IGameObject {
    private String name;
    private Transform transform;
    private Collider collider;

    GameObject(String name,
                      double x, double y, int layer, double angle, double scale,
                      Figura fig){

        this.name = name;

        transform = new Transform(x, y, layer, angle, scale);

        collider = new Collider(fig, transform);
    }


    public void move(Point p, int s){
        transform.move(p, s);
        collider.move(p, s);
    }

    public void rotate(double r){
        transform.rotate(r);
        collider.rotate(r);
    }

    public void scale(double s){
        transform.scale(s);
        collider.scale(s);
    }



    public String name(){
        return name;
    }

    public Transform transform(){
        return transform;
    }

    public Collider collider(){
        return collider;
    }

}
