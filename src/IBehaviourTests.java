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



    @Test
    public void onEverythingExceptColisionTest(){ //onColision just runs everytime update runs
        GameEngine ge = new GameEngine();
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square1 = new Polygon(pts1);
        testBehaviour behaviour = new testBehaviour();
        GameObject go1 = new GameObject("Square", 2, 2, 0, 0, 1, square1, behaviour);

        ge.addEnabled(go1);
        assertEquals(true, behaviour.onInitRan);
        ge.disable(go1);
        assertEquals(true, behaviour.onDisableRan);
        ge.enable(go1);
        assertEquals(true, behaviour.onEnableRan);
        ge.destroy(go1);
        assertEquals(true, behaviour.onDestroyRan);
        

    }    
}
