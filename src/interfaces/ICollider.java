package interfaces;

import geometry.Figure;
import geometry.Point;

/**
 * Interface do collider.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public interface ICollider {

    public ITransform transform();

    public  void ITransform(ITransform transform);

    public Point centroid();

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

    public Figure figure();

    @Override
    public String toString();
}
