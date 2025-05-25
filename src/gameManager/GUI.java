package gameManager;

import engine.GameEngine;
import engine.InputManager;
import java.awt.Canvas;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Classe responsável por criar a janela do jogo e gerir o ciclo de vida
 * do Canvas onde o motor de jogo irá desenhar os objetos.
 * Utiliza BufferStrategy para otimizar a renderização e um thread
 * separado para correr o motor de jogo.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 19/05/2025
 */
public class GUI extends Canvas {

    private static final int width  = 1440;
    private static final int height = 810;
    private final GameEngine engine;

    /**
     * Configura o Canvas, adiciona o listener de teclado,
     * cria a janela e inicia o loop do GameEngine
     */
    public GUI() {
        setSize(width, height);
        setIgnoreRepaint(true);
        setFocusable(true);

        InputManager input = new InputManager();
        addKeyListener(input);

        engine = GameEngine.getInstance();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Engine Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(this);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            engine.setWindow(this);

            Loader.loadLevel(1, 1);

            new Thread(engine::run, "EngineThread").start();
        });
    }
}
