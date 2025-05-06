package gui;

import engine.*;
import interfaces.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class Loader {
    
    private static final GameEngine engine = GameEngine.getInstance();
    private static int posKey;

    private static void level1(){

        IGameObject player = ObjectCreator.Player(400, 300);
        engine.addEnabled(player);


        IGameObject enemy = ObjectCreator.Enemy1(100, 300);
        engine.addEnabled(enemy);

        // lag test
        // o lag n vem das colisoes como podem ver
        for(int i = 0; i < 300; i++){
            enemy = ObjectCreator.Enemy1(i*200, 800);
            engine.addEnabled(enemy);
        }

        IGameObject ls = ObjectCreator.loading_screen(1000, 500, 2, 1);
        engine.addEnabled(ls);
    }

    private static  void level2(){

        IGameObject player = ObjectCreator.Player(400, 300);
        engine.addEnabled(player);

        IGameObject enemy = ObjectCreator.Enemy1(800, 600);
        engine.addEnabled(enemy);

        IGameObject ls = ObjectCreator.loading_screen(1000, 500, 1, 1);
        engine.addEnabled(ls);

    }





    public static void loadLevel(int roomKey, int posKey) {

        engine.destroyAll();

        Loader.posKey = posKey;

        String methodName = "level" + roomKey;
        try{
            Method method = Loader.class.getDeclaredMethod(methodName);
            method.invoke(null);
        }
        catch(NoSuchMethodException NSME){
            System.out.println("you tried to load a level that doesnt exist good job");
        }
        catch(IllegalAccessException IAE){
            System.out.println("IllegalAccessException how are you seeing this wtf");
        }
        catch(InvocationTargetException e){
            System.out.println("InvocationTargetException how are you seeing this wtf");
        }
    }
}