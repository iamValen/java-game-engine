
/**
 * Representa um objeto.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class GameObject implements IGameObject {
    private final String name;
    private ITransform transform;
    private IShape shape;
    private ICollider collider;
    private IBehaviour behaviour;



    /**
     * Construtor da classe GameObject.
     * 
     * @param name Nome do objeto.
     * @param x Posição X inicial.
     * @param y Posição Y inicial.
     * @param layer Camada do objeto.
     * @param angle Ângulo de rotação inicial.
     * @param scale Fator de escala inicial.
     * @param fig Figura associada ao objeto.
     */
    GameObject(String name,
               double x, double y, int layer, double angle, double scale,
               Figure fig) {
        this.name = name;
        this.transform = new Transform(x, y, layer, angle, scale);
        this.collider = new Collider(fig);
        this.collider.setTransform(transform);
    }

    GameObject(String name,
            double x, double y, int layer, double angle, double scale,
            Figure fig, IBehaviour behaviour) {
        this.name = name;
        this.transform = new Transform(x, y, layer, angle, scale);
        this.collider = new Collider(fig);
        this.collider.setTransform(transform);
        this.behaviour = behaviour;
    }


    /*
     * empty constructor always use this one then insert elements later
     * others are for testing
     */
    public GameObject(String name){
        this.name = name;
    }
    /**
     * always call this after initializing a GameObject
     * collider and shape always depend on transform
     * behaviour can depend on anything depending on implementation
     * dont set dependable members to null
     */
    public void insertElements(ITransform transform, ICollider collider,
    Shape shape, IBehaviour behaviour){
        this.transform = transform;
        this.collider = collider;
        this.behaviour = behaviour;
        this.shape = shape;


        this.collider.setTransform(transform);
        this.collider.onUpdate();

        this.behaviour.setGO(this);
    }



    /**
     * Retorna o nome do objeto.
     * 
     * @return Nome do objeto.
     */
    @Override
    public String name(){
        return this.name;
    }

    /**
     * Retorna o transform associado ao objeto.
     * 
     * @return Objeto Transform contendo posição, rotação e escala.
     */
    @Override
    public ITransform transform(){
        return this.transform;
    }

    /**
     * Retorna o collider do objeto.
     * 
     * @return O Collider associado ao objeto.
     */
    @Override
    public ICollider collider(){
        return this.collider;
    }

    @Override
    public  IShape shape(){
        return this.shape;
    }

    @Override
    public  IBehaviour behaviour(){
        return this.behaviour;
    }
}
