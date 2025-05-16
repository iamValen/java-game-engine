package interfaces;

public interface Observer {
    void update(int health);

    /**
     * 
     * @return 0 - Health Observer
     * @return 1 - Score Observer
     */
    int type();
}