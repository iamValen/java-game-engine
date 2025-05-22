package gui;

import engine.*;
import figures.Point;
import interfaces.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Classe responsável por carregar diferentes níveis do jogo dinamicamente.
 * Utiliza reflexão para invocar métodos de configuração de cada nível
 * (level1, level2, etc.) e limpa o estado atual do motor antes de carregar.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
@SuppressWarnings("unused")
public class Loader {
    /** Instância única do motor de jogo */
    private static final GameEngine engine = GameEngine.getInstance();
    /** Chave de posição dentro do nível (pode indicar spawn point) */
    private static int posKey;

    private static IGameObject player;

    /**
     * Configuração da sala 1.
     */

    public static void gameOver(){
        IGameObject background = ObjectCreator.background(engine.getScreenWidth()/2, engine.getScreenHeight()/2, Integer.MIN_VALUE, 0, 1, engine.getScreenWidth(), engine.getScreenHeight());
        engine.addEnabled(background);
        engine.destroyAll();
        player = null;
        IGameObject gameOver = ObjectCreator.gameOver();
        engine.addEnabled(gameOver);
    }

    private static void level1(){
        IGameObject background = ObjectCreator.background(engine.getScreenWidth()/2, engine.getScreenHeight()/2, Integer.MIN_VALUE, 0, 1, engine.getScreenWidth(), engine.getScreenHeight());
        engine.addEnabled(background);

        IGameObject floor = ObjectCreator.floor();
        engine.addEnabled(floor);

        int screenW = engine.getScreenWidth();
        int screenH = engine.getScreenHeight();

        IGameObject leftWall = ObjectCreator.block(-20, screenH / 2, 0, 0, 1, 30, screenH, false);
        engine.addEnabled(leftWall);

        IGameObject rightWall = ObjectCreator.block(screenW, screenH / 2, 0, 0, 1, 10, screenH, false);
        engine.addEnabled(rightWall);


        if(player == null) 
            player = ObjectCreator.Player(150, 700, 0, 0, 1, 40, 100);
        else
            player.transform().setPosition(new Point(500, 300), 0);    
        engine.addEnabled(player);


        IGameObject enemy1 = ObjectCreator.Enemy1(50, 450, 0, 0, 1, 50, 50, 600, 300);
        engine.addEnabled(enemy1);

         IGameObject enemy2 = ObjectCreator.Enemy1(1400, 600, 0, 0, 1, 50, 50, 600, 300);
        engine.addEnabled(enemy2);

        

        IGameObject block1 = ObjectCreator.block(1500,300, 0, 0, 1, 2304, 64, true);
        engine.addEnabled(block1);

        IGameObject block2 = ObjectCreator.block(0,550, 0, 0, 1, 2304, 64, true);
        engine.addEnabled(block2);


        IGameObject ls = ObjectCreator.loading_screen(1400, 200, 0, 0, 1, 60, 60, 2, 1);
        engine.addEnabled(ls);
    }

    /**
     * Configuração da sala 2.
     */
    private static void level2(){
        IGameObject background = ObjectCreator.background(engine.getScreenWidth()/2, engine.getScreenHeight()/2, Integer.MIN_VALUE, 0, 1, engine.getScreenWidth(), engine.getScreenHeight());
        engine.addEnabled(background);

        IGameObject floor = ObjectCreator.floor();
        engine.addEnabled(floor);

        int screenW = engine.getScreenWidth();
        int screenH = engine.getScreenHeight();

        IGameObject leftWall = ObjectCreator.block(-20, screenH / 2, 0, 0, 1, 30, screenH, false);
        engine.addEnabled(leftWall);

        IGameObject rightWall = ObjectCreator.block(screenW, screenH / 2, 0, 0, 1, 10, screenH, false);
        engine.addEnabled(rightWall);

        
        if(player == null) 
            player = ObjectCreator.Player(500, 300, 0, 0, 1, 40, 100);
        else
            player.transform().setPosition(new Point(500, 300), 0);    
        engine.addEnabled(player);

        IGameObject enemy = ObjectCreator.Enemy1(1400, 600, 0, 0, 1, 50, 50, 400, 600);
        engine.addEnabled(enemy);

        //IGameObject ls = ObjectCreator.loading_screen(1000, 600, 0, 0, 1, 60, 60, 1, 1);
        //engine.addEnabled(ls);
    }


    /**
     * Carrega dinamicamente o nível indicado por roomKey e posiciona
     * o jogador conforme posKey. Se o nível não existir, apresenta mensagem de erro.
     * 
     * @param roomKey identificador do nível a carregar
     * @param posKey  identificador da posição de spawn dentro do nível
     */
    public static void loadLevel(int roomKey, int posKey) {
        // Limpa todos os objetos atualmente ativos no motor
        engine.destroyAll();

        // Guarda a posição para uso futuro, se necessário
        Loader.posKey = posKey;

        // Monta o nome do método do nível a invocar
        String methodName = "level" + roomKey;
        try{
            Method method = Loader.class.getDeclaredMethod(methodName);
            method.invoke(null);
        }
        catch(NoSuchMethodException NSME){
            System.out.println("Tentativa de carregar nível inexistente: " + methodName);
        }
        catch(IllegalAccessException IAE){
            System.out.println("Erro de acesso ao método " + methodName);
        }
        catch(InvocationTargetException ITE){
            // Mostra stack trace da exceção interna lançada pelo método
            ITE.getCause().printStackTrace(System.out);
        }
    }
}
