package game;

import interfaces.Observer;
import shapes.ScoreShape;

/**
 * Atualiza a pontuação no HUD com base nas notificações
 * quando é chamado pela classe observada
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class HUDScoreBehaviour extends AAABehaviour implements Observer {
    
    private final ScoreShape ss;

    /** 
     * Construtor 
     * 
     * @param ss Forma da pontuação
     */
    public HUDScoreBehaviour(ScoreShape ss){
        this.ss = ss;
    }

    @Override
    public void update(ObserverInfo info) {
        ss.update(info.i);
    }
}
