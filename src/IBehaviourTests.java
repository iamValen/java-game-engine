import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class IBehaviourTests {



    class testBehaviour implements IBehaviour{
        boolean onInitRan = false;
        boolean onEnableRan = false;
        boolean onDisableRan = false;
        boolean onDestroyRan = false;
        public void onCollision(ArrayList<IGameObject> gol){}
        public void oninit(){onInitRan = true;}
        public void onEnable(){onEnableRan = true;}
        public void onDisable(){onDisableRan = true;}
        public void onDestroy(){onDestroyRan = true;}
    }


    GameObject exampleGameObject(testBehaviour behaviour){
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square1 = new Polygon(pts1);
        return new GameObject("Square", 2, 2, 0, 0, 1, square1, behaviour);
    }



    @Test
    public void oninitTest0(){
        GameEngine ge = new GameEngine();
        testBehaviour behaviour = new testBehaviour();
        GameObject go1 = exampleGameObject(behaviour);

        ge.addEnabled(go1);
        assertEquals(true, behaviour.onInitRan);
    }

    @Test
    public void oninitTest1(){
        GameEngine ge = new GameEngine();
        testBehaviour behaviour = new testBehaviour();
        GameObject go1 = exampleGameObject(behaviour);

        ge.addDisabled(go1);
        assertEquals(true, behaviour.onInitRan);
    }


    @Test
    public void onDisableTest(){
        GameEngine ge = new GameEngine();
        testBehaviour behaviour = new testBehaviour();
        GameObject go1 = exampleGameObject(behaviour);

        ge.addEnabled(go1);
        ge.disable(go1);
        assertEquals(true, behaviour.onDisableRan);
    }


    
    @Test
    public void onEnableTest(){
        GameEngine ge = new GameEngine();
        testBehaviour behaviour = new testBehaviour();
        GameObject go1 = exampleGameObject(behaviour);

        ge.addDisabled(go1);
        ge.enable(go1);
        assertEquals(true, behaviour.onEnableRan);
    }


    
    @Test
    public void onDestroyTest(){
        GameEngine ge = new GameEngine();
        testBehaviour behaviour = new testBehaviour();
        GameObject go1 = exampleGameObject(behaviour);

        ge.addEnabled(go1);
        ge.destroy(go1);
        assertEquals(true, behaviour.onDestroyRan);
    }
}
