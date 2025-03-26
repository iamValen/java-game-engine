public class GameObject implements IGameObject {
    private String name;
    private Transform transform;
    private Collider collider;

    GameObject(String name,
                      double x, double y, int layer, double rotation, double scale,
                      Figura fig){

        this.name = name;

        transform = new Transform(x, y, layer, rotation, scale);

        collider = new Collider(fig, transform);
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
