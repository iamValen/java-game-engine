package shapes;

import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Barra de vida na tela
 * 
 * Desenha uma barra de vida com preenchimento proporcional à vida atual
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class HealthShape implements IShape {

    private final int barHeight = 40;
    private final int barWidth;
    private int filledWidth;

    /**
     * Construtor.
     * 
     * @param width largura total da barra de vida
     */
    public HealthShape(int width){
        this.barWidth = width;
    }

    /**
     * Atualiza o valor da largura da barra preenchida
     * 
     * @param filledHealthWidth largura proporcional à vida atual
     */
    public void update(int filledHealthWidth){
        this.filledWidth = filledHealthWidth;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y - barHeight / 2, barWidth, barHeight);

        g.setColor(Color.GREEN);
        g.fillRect(x, y - barHeight / 2, filledWidth, barHeight);
    }
}
