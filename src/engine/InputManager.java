package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe responsável pela gestão do input do teclado.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class InputManager implements KeyListener {

    // Conjunto estático que armazena os códigos das teclas atualmente pressionadas
    private static final Set<Integer> pressed = new HashSet<>();

    /**
     * Método chamado quando uma tecla é pressionada.
     * Adiciona o código da tecla ao conjunto de teclas pressionadas.
     * 
     * @param e evento associado à tecla pressionada
     */
    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
    }

    /**
     * Método chamado quando uma tecla é libertada.
     * Remove o código da tecla do conjunto de teclas pressionadas.
     * 
     * @param e evento associado à tecla libertada
     */
    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }

    /**
     * Método chamado quando uma tecla é digitada (pressionada e libertada).
     * Ainda não implementado nem utilizado.
     * 
     * @param e evento associado à tecla digitada
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Verifica se uma determinada tecla está atualmente pressionada.
     * 
     * @param keyCode código da tecla a verificar (ex: KeyEvent.VK_LEFT)
     * @return true se a tecla estiver pressionada, false caso contrário
     */
    public static boolean isKeyDown(int keyCode) {
        return pressed.contains(keyCode);
    }
}
