package game;

import interfaces.Observer;
import shapes.DashShape;

/**
 * Atualiza a barra de dash no HUD com base no tempo de recarga
 * quando é chamado pela classe observada
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class HUDDashBehaviour extends AAABehaviour implements Observer {

    private final DashShape ds;
    private final long dashRechargeTime = 3000;
    private long lastDashRechargeTime = -1;
    private final int barWidth;

    /** 
     * Construtor 
     * 
     * @param width Largura da barra
     * @param shape Forma da barra
     */
    public HUDDashBehaviour(int width, DashShape shape){
        this.barWidth = width;
        this.ds = shape;
    }

    /** 
     * Atualiza o estado visual com base no progresso da recarga 
     * 
     * @param info Informações do observador
     */
    @Override
    public void update(ObserverInfo info) {
        long now = System.currentTimeMillis();
        int charges = info.i;
        lastDashRechargeTime = info.l;

        float progress = (float)(now - lastDashRechargeTime) / dashRechargeTime;
        progress = Math.max(0f, Math.min(1f, progress));
        int filledWidth = (int)(progress * barWidth);

        ds.update(charges, lastDashRechargeTime, filledWidth);
    }
}
