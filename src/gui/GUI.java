package gui;

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
 * @version 11/05/2025
 */
public class GUI extends Canvas {

    private static final int WIDTHT  = 1440;
    private static final int HEIGHTT = 810;
    private final GameEngine engine;

    /**
     * Configura o Canvas, adiciona o listener de teclado,
     * cria a janela e inicia o loop do motor de jogo.
     */
    public GUI() {
        // Define as dimensões do Canvas
        setSize(WIDTHT, HEIGHTT);
        // Desliga paint automático; usaremos BufferStrategy
        setIgnoreRepaint(true);
        // Permite receber eventos de teclado
        setFocusable(true);

        // Cria e regista o InputManager para capturar teclas
        InputManager input = new InputManager();
        addKeyListener(input);

        // Obtém a instância singleton do GameEngine
        engine = GameEngine.getInstance();

        // Executa no Event Dispatch Thread para criar a janela
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Engine Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(this);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Prepara o BufferStrategy para renderização eficiente
            engine.setWindow(this);

            // Carrega o nível inicial (sala 1, posição 1)
            Loader.loadLevel(2, 1);

            // Inicia o engine num thread separado
            new Thread(engine::run, "EngineThread").start();
        });
    }
}
