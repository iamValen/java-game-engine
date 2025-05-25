package shapes;

import java.awt.Color;
import java.awt.Graphics;

import interfaces.IShape;

/**
 * Desenha a barra de cargas de dash do jogador
 * 
 * Representa visualmente até duas cargas com animação em tempo real do carregamento
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class DashShape implements IShape {

    private int dashCharges = 2;
    private final int maxDashCharges = 2;
    private long lastDashRechargeTime = -1;

    private final int barWidth = 100;
    private final int barHeight = 15;
    private final int barSpacing = 10;

    private int filledWidth;

    /**
     * Atualiza o estado da barra de dash
     * 
     * @param dashCharges número de cargas disponíveis
     * @param lastDashRechargeTime tempo desde o início da recarga
     * @param filledWidth largura preenchida da barra em recarga
     */
    public void update(int dashCharges, long lastDashRechargeTime, int filledWidth){
        this.dashCharges = dashCharges;
        this.lastDashRechargeTime = lastDashRechargeTime;
        this.filledWidth = filledWidth;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        for (int i = 0; i < maxDashCharges; i++) {
            int barX = x + i * (barWidth + barSpacing);
            int barY = y + barHeight + barSpacing;

            g.setColor(Color.GRAY);
            g.fillRect(barX, barY, barWidth, barHeight);

            if (i < dashCharges) { // barras cheias
                g.setColor(Color.CYAN);
                g.fillRect(barX, barY, barWidth, barHeight);
            } else if (i == dashCharges && lastDashRechargeTime != -1) { // barras a recarregar
                g.setColor(Color.CYAN);
                g.fillRect(barX, barY, filledWidth, barHeight);
            }
        }
    }
}
