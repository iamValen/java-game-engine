/**
 * Representa um objeto
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class GameObject implements IGameObject {
    private String name;
    private Transform transform;
    private Collider collider;
    
    public Point posSpeed;
    public double layerSpeed;
    public double rotationSpeed;
    public double scaleSpeed;

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
               Figura fig) {
        this.name = name;
        transform = new Transform(x, y, layer, angle, scale);
        collider = new Collider(fig, transform);
    }

    /**
     * Move o objeto para uma nova posição e camada.
     * 
     * @param p Ponto para onde o objeto será movido.
     * @param s Quantidade a adicionar à camada.
     */
    public void move(Point p, int s){
        transform.move(p, s);
    }

    /**
     * Rotaciona o objeto.
     * 
     * @param r Ângulo em graus pelo qual o objeto será rotacionado.
     */
    public void rotate(double r){
        transform.rotate(r);
    }

    /**
     * Redimensiona o objeto.
     * 
     * @param s Fator de escala a aplicar.
     */
    public void scale(double s){
        transform.scale(s);
    }

    
    public void update(){
        collider.updateFig();
    }


    public void generateNextFrame(){
        transform.move(posSpeed, (int) layerSpeed);
        transform.rotate(rotationSpeed);
        transform.scale(scaleSpeed);
        collider.updateFig();
    }



    /**
     * Retorna o nome do objeto.
     * 
     * @return Nome do objeto.
     */
    public String name(){
        return name;
    }

    /**
     * Retorna o transform associado ao objeto.
     * 
     * @return Objeto Transform contendo posição, rotação e escala.
     */
    public Transform transform(){
        return transform;
    }

    /**
     * Retorna o collider do objeto.
     * 
     * @return O Collider associado ao objeto.
     */
    public Collider collider(){
        return collider;
    }
}