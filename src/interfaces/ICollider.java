package interfaces;

import figures.Point;

/**
 * Interface do collider.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public interface ICollider {
    Point centroid();

    public void setTransform(ITransform transform);


    /**
     * Atualiza a figura com base na transformação aplicada.
     * 
     */
    public void onUpdate();

    /**
     * Verifica se este Collider colide com outro Collider.
     * 
     * @param that Outro Collider a ser testado.
     * @return true se houver colisão, false caso contrário.
     */
    public boolean isColliding(ICollider that);

    @Override
    public String toString();
}
