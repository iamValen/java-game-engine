import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.management.monitor.GaugeMonitor;

import org.junit.jupiter.api.Test;

public class GameEngineTests {
    @Test
    public void testAdd(){
        GameEngine ge = new GameEngine();
        assertEquals(0, ge.gameObjects().size());

        Point[] pts = new Point[4];
        pts[0] = new Point(4, 3);
        pts[1] = new Point(4, 7);
        pts[2] = new Point(7, 7);
        pts[3] = new Point(7, 3);
        Polygon p = new Polygon(pts);
        GameObject go1 = new GameObject("Rect", 2, 2, 0, 0, 1, p);
        go1.posSpeed = new Point(0, 0);
        go1.layerSpeed = 0;
        go1.rotationSpeed = 0;
        go1.scaleSpeed = 0;
        ge.add(go1);

        assertEquals(1, ge.gameObjects().size());
    }

    @Test
    public void testDestroy(){
        GameEngine ge = new GameEngine();
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 3);
        pts[1] = new Point(4, 7);
        pts[2] = new Point(7, 7);
        pts[3] = new Point(7, 3);
        Polygon p = new Polygon(pts);
        GameObject go1 = new GameObject("Rect", 2, 2, 0, 0, 1, p);
        go1.posSpeed = new Point(0, 0);
        go1.layerSpeed = 0;
        go1.rotationSpeed = 0;
        go1.scaleSpeed = 0;
        ge.add(go1);

        ge.destroy(go1);

        assertEquals(0, ge.gameObjects().size());
    }

    @Test
    public void testGenerateNextFrameAndSimlateFrames(){
        GameEngine ge = new GameEngine();
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 3);
        pts[1] = new Point(4, 7);
        pts[2] = new Point(7, 7);
        pts[3] = new Point(7, 3);
        Polygon p = new Polygon(pts);
        GameObject go1 = new GameObject("Rect", 2, 2, 0, 0, 1, p);
        go1.posSpeed = new Point(0, 0);
        go1.layerSpeed = 1;
        go1.rotationSpeed = 0;
        go1.scaleSpeed = 0;
        ge.add(go1);
        
        ge.simulateFrames(2);
        assertEquals(2, ge.gameObjects().get(0).transform().layer());
    }

    @Test
    public void testDetectCollisions(){
        GameEngine ge = new GameEngine();
        // Collides
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square = new Polygon(pts1);
        GameObject go1 = new GameObject("Square", 2, 2, 0, 0, 1, square);
        go1.posSpeed = new Point(2, 1);
        go1.layerSpeed = 0;
        go1.rotationSpeed = 0;
        go1.scaleSpeed = 0;
        ge.add(go1);

        Point[] pts2 = new Point[4];
        pts2[0] = new Point(4, 3);
        pts2[1] = new Point(4, 7);
        pts2[2] = new Point(7, 7);
        pts2[3] = new Point(7, 3);
        Polygon rect = new Polygon(pts2);
        GameObject go2 = new GameObject("Rect", 5, 5, 0, 0, 1, rect);
        go2.posSpeed = new Point(0, 0);
        go2.layerSpeed = 0;
        go2.rotationSpeed = 0;
        go2.scaleSpeed = 0;
        ge.add(go2);

        GameObject go3 = new GameObject("Circle", 10, 4, 0, 0, 1, new Circle(new Point(10, 4), 1d));        
        go3.posSpeed = new Point(-4, 0);
        go3.layerSpeed = 0;
        go3.rotationSpeed = 0;
        go3.scaleSpeed = 0;
        ge.add(go3);

        ge.simulateFrames(1);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Square Rect Circle");
        expected.add("Rect Square Circle");
        expected.add("Circle Square Rect");

        assertEquals(expected, ge.checkCollisions());

        ge.destroy(go1);
        ge.destroy(go2);
        ge.destroy(go3);
        
        // Don't collide
        GameObject go4 = new GameObject("bullet", 1.5, 2.5, 4, 0, 1, new Circle(new Point(1.5, 2.5), 1d));        
        go4.posSpeed = new Point(2, 0);
        go4.layerSpeed = 0;
        go4.rotationSpeed = 0;
        go4.scaleSpeed = 0;
        ge.add(go4);

        Point[] pts3 = new Point[4];
        pts3[0] = new Point(8, 1);
        pts3[1] = new Point(8, 4);
        pts3[2] = new Point(9, 4);
        pts3[3] = new Point(9, 1);
        Polygon target = new Polygon(pts2);
        GameObject go5 = new GameObject("target", 8.5, 2.5, 4, 0, 1, target);
        go5.posSpeed = new Point(0, 0);
        go5.layerSpeed = 0;
        go5.rotationSpeed = 0;
        go5.scaleSpeed = 0;
        ge.add(go5);

        expected = new ArrayList<>();
        assertEquals(expected, ge.checkCollisions());
    }





    @Test
    public void testAddEnabled(){
        GameEngine ge = new GameEngine();
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square = new Polygon(pts1);
        GameObject go1 = new GameObject("Square", 2, 2, 0, 0, 1, square);
        ge.addEnabled(go1);
        assertEquals(true, ge.enabled().contains(go1));
    }

    @Test
    public void testAddDisabled(){
        GameEngine ge = new GameEngine();
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square = new Polygon(pts1);
        GameObject go1 = new GameObject("Square", 2, 2, 0, 0, 1, square);
        ge.addDisabled(go1);
        assertEquals(true, ge.disabled().contains(go1));
    }

    @Test
    public void testDisable(){
        GameEngine ge = new GameEngine();
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square = new Polygon(pts1);
        IGameObject go1 = new GameObject("Square", 2, 2, 0, 0, 1, square);
        ge.addEnabled(go1);
        ge.disable(go1);
        assertEquals(true, ge.isDisabled(go1));
        assertEquals(false, ge.enabled().contains(go1));
        assertEquals(true, ge.disabled().contains(go1));
    }


    @Test
    public void testEnable(){
        GameEngine ge = new GameEngine();
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square = new Polygon(pts1);
        GameObject go1 = new GameObject("Square", 2, 2, 0, 0, 1, square);
        ge.addDisabled(go1);
        ge.enable(go1);
        assertEquals(true, ge.isEnabled(go1));
        assertEquals(true, ge.enabled().contains(go1));
        assertEquals(false, ge.disabled().contains(go1));
    }


    @Test
    public void testDestroyAll(){
        GameEngine ge = new GameEngine();
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square1 = new Polygon(pts1);
        GameObject go1 = new GameObject("Square", 2, 2, 0, 0, 1, square1);

        Point[] pts2 = new Point[4];
        pts2[0] = new Point(4, 3);
        pts2[1] = new Point(4, 7);
        pts2[2] = new Point(7, 7);
        pts2[3] = new Point(7, 3);
        Polygon square2 = new Polygon(pts2);
        GameObject go2 = new GameObject("Rect", 2, 2, 0, 0, 1, square2);

        ge.addEnabled(go1);
        ge.addEnabled(go2);

        assertEquals(false, ge.enabled().contains(go1));
        assertEquals(false, ge.enabled().contains(go2));
    }



}
