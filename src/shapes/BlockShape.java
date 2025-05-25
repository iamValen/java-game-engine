package shapes;

import interfaces.IShape;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Representa um bloco colorido com largura e altura definidas
 * Usado para desenhar plataformas ou obstáculos sem imagens
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class BlockShape implements IShape {

    private final int width;
    private final int height;
    private Color color;

    /**
     * Construtor
     * 
     * @param width Largura do bloco
     * @param height Altura do bloco
     * @param color Cor do bloco
     */
    public BlockShape(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(x - this.width / 2, y - this.height / 2, this.width, this.height);
    }

    /**
     * Altera a cor do bloco
     * 
     * @param color nova cor
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
