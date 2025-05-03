package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Canvas;
import engine.InputManager;
import engine.GameEngine;

public class GUI extends Canvas {

    private static final int WIDTH  = 1920;
    private static final int HEIGHT = 1080;
    private final GameEngine engine;

    public GUI() {
        // define tamanho do Canvas
        setSize(WIDTH, HEIGHT);
        setIgnoreRepaint(true);   // painting controlado pela BufferStrategy
        setFocusable(true);       // permite receber KeyEvents

        // adiciona o listener de teclado
        InputManager input = new InputManager();
        addKeyListener(input);

        // obtém instância do motor
        engine = GameEngine.getInstance();

        // Cria e exibe a janela no EDT
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Engine Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(this);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // agora que o Canvas está visível, prepara o buffer
            engine.setRenderSurface(this);

            // carrega o nível e inicia o loop do motor
            new Loader().loadLevel();
            new Thread(engine::run, "EngineThread").start();
            
            //engine.run(); // não funfa
        });
    }

    public static void main(String[] args) {
        new GUI();
    }
}
