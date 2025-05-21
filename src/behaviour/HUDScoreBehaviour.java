package behaviour;

import interfaces.Observer;
import shapes.ScoreShape;

public class HUDScoreBehaviour extends AAABehaviour implements Observer {
    
    private final ScoreShape ss;

    public HUDScoreBehaviour(ScoreShape ss){
        this.ss = ss;
    }

    @Override
    public void update(ObserverInfo info) {
        ss.update(info.i);
    }
}
