package interfaces;

import behaviour.PlayerBehaviour;

public interface Observer {
    void update(PlayerBehaviour playerB);

    /**
     * 
     * @return 0 - Health Observer
     * @return 1 - Score Observer
     */
    int type();
}