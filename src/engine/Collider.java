package engine;
import figures.Figure;
import figures.Point;
import interfaces.ICollider;
import interfaces.ITransform;

/**
 * Representa o colisor de um objeto.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class Collider implements ICollider {
    private Figure fig;
    private ITransform transform;
    private Point centroid;
    private double angleOld;
    private double scaleOld;

    /**
     * Construtor da classe Collider.
     * 
     * @param fig Figura associada ao collider.
     */
    public Collider(Figure fig){
        this.fig = fig;
        this.centroid = fig.centroid();
        this.transform = null;
        this.angleOld = 0;
        this.scaleOld = 1;
    }

    /**
     * this is called by the GameObject when the collider is inserted onto it
     * DO NOT CALL IT BEFORE THAT OR A METEOR WILL FALL ON YOU
     * you shouldnt ever call this method anyway
     */
    @Override
    public void setTransform(ITransform transform){
        if(this.transform == null){
            this.transform = transform;
            this.onUpdate();
        }
    }

    /**
     * Atualiza a figura com base nos dados do transform
     * 
     */
    @Override
    public void onUpdate(){
        this.fig = fig.translation(centroid.flipSign()); // Move para a origem
        this.fig = fig.rotate(transform.angle() - this.angleOld); // Aplica rotação
        this.angleOld = transform.angle();
        this.fig = fig.scale(transform.scale() / this.scaleOld); // Aplica escala
        this.scaleOld = transform.scale();
        this.centroid = transform.position();
        this.fig = fig.translation(this.centroid); // Move para a posição correta
    }

    /**
     * Retorna o centroid da figura
     * 
     * @return ponto que representa o centroide da figura.
     */
    @Override
    public Point centroid() {
        return this.centroid;
    }

    /**
     * Verifica se este Collider colide com outro Collider.
     * 
     * @param other Outro Collider a ser testado.
     * @return true se houver colisão, false caso contrário.
     */
    @Override
    public boolean isColliding(ICollider that) {
        return this.fig.collision(that.getHitbox());
    }

    @Override
    public Figure getHitbox(){
        return this.fig;
    }

    @Override
    public String toString() {
        return fig.toString();
    }
}
