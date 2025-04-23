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

    /**
     * Atualiza a figura com base na transformação aplicada.
     * 
     */
    public void updateFig();

    /**
     * Verifica se este Collider colide com outro Collider.
     * 
     * @param other Outro Collider a ser testado.
     * @return true se houver colisão, false caso contrário.
     */
    public boolean collidesWith(ICollider other);

    public String toString();
}
