package interfaces;

/**
 * Classe Observable do padrão de projeto Observers
 * Usada para notificar o HUD de quando deve ser alterado (Vida, Dash e Score)
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public interface Observable {
    /**
     * Adiciona um observer
     * 
     * @param observer Observer
     */
    void addObserver(Observer observer);

    /**
     * Remove um observer
     * 
     * @param observer Observer
     */
    void removeObserver(Observer observer);

    /**
     * Notifica o HealthBehaviour
     */
    void notifyHealth();

    /**
     * Notifica o DashBehaviour
     */
    void notifyDash();

    /**
     * Notifica o ScoreBehaviour
     */
    void notifyScore();
}