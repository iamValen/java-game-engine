package interfaces;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyHealth();
    void notifyDash();
    void notifyScore();
}