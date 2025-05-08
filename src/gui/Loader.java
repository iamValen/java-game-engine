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

        IGameObject player = ObjectCreator.Player(500, 300);
        engine.addEnabled(player);


        IGameObject enemy = ObjectCreator.Enemy1(100, 300);
        engine.addEnabled(enemy);

        IGameObject ls = ObjectCreator.loading_screen(1000, 500, 2, 1);
        engine.addEnabled(ls);

        IGameObject floor = ObjectCreator.floor();
        engine.addEnabled(floor);

        System.out.println("level1 loaded");

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
        catch(InvocationTargetException ITE){
            System.out.println("InvocationTargetException how are you seeing this wtf");
        }
    }
}