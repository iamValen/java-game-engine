package interfaces;

import java.awt.Graphics;

/**
 * Interface que representa a forma visual de um objecto do jogo.
 * Define os métodos necessários para desenhar e alterar a cor da forma.
 * 
 * @author —
 * @version —
 */
public interface IShape {

    /**
     * Renderiza a forma no ecrã.
     * @param g Contexto gráfico onde desenhar.
     * @param x Coordenada x onde desenhar.
     * @param y Coordenada y onde desenhar.
     */
    public void render(Graphics g, int x, int y);

}
