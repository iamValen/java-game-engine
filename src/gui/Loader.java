package gui;

import engine.*;
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
public class Loader {
    /** Instância única do motor de jogo */
    private static final GameEngine engine = GameEngine.getInstance();
    /** Chave de posição dentro do nível (pode indicar spawn point) */
    private static int posKey;

    /**
     * Configuração da sala 1.
     */
    private static void level1(){
        IGameObject player = ObjectCreator.Player(500, 300);
        engine.addEnabled(player);

        IGameObject enemy = ObjectCreator.Enemy1(100, 300);
        engine.addEnabled(enemy);

        IGameObject ls = ObjectCreator.loading_screen(1000, 600, 2, 1);
        engine.addEnabled(ls);

        IGameObject floor = ObjectCreator.floor();
        engine.addEnabled(floor);
        
        IGameObject block = ObjectCreator.block(1300,400,200,200);
        engine.addEnabled(block);

        IGameObject HUDplayerHealth = ObjectCreator.healthHUD();
        engine.addEnabled(HUDplayerHealth);
    }

    /**
     * Configuração da sala 2.
     */
    private static void level2(){
        IGameObject player = ObjectCreator.Player(400, 300);
        engine.addEnabled(player);

        IGameObject enemy = ObjectCreator.Enemy1(800, 600);
        engine.addEnabled(enemy);

        IGameObject ls = ObjectCreator.loading_screen(1000, 600, 1, 1);
        engine.addEnabled(ls);

        IGameObject floor = ObjectCreator.floor();
        engine.addEnabled(floor);

        enemy = ObjectCreator.Enemy1(4000, 600);
        engine.addEnabled(enemy);

        IGameObject HUDplayerHealth = ObjectCreator.healthHUD();
        engine.addEnabled(HUDplayerHealth);
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
