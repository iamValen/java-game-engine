package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import behaviour.PlayerBehaviour;
import engine.Collider;
import engine.GameEngine;
import engine.GameObject;
import engine.Physics;
import engine.Transform;
import figures.Circle;
import figures.Point;
import figures.Polygon;

public class GameEngineTests {
    @Test
    public void testAdd(){
        GameEngine ge = GameEngine.getInstance();
        assertEquals(0, ge.enabled().size());
        assertEquals(0, ge.disabled().size());

        Point[] pts = new Point[4];
        pts[0] = new Point(4, 3);
        pts[1] = new Point(4, 7);
        pts[2] = new Point(7, 7);
        pts[3] = new Point(7, 3);
        Polygon p = new Polygon(pts);
        GameObject go1 = new GameObject("Rect");
        Transform t1 = new Transform(2, 2, 0, 0, 1);
        Collider c1 = new Collider(p);
        PlayerBehaviour behav = new PlayerBehaviour(go1);
        go1.insertElements(t1, c1, null, behav);

        ge.addEnabled(go1);

        assertEquals(1, ge.enabled().size());
        assertEquals(0, ge.disabled().size());

        GameObject go2 = new GameObject("Rect");
        Transform t2 = new Transform(2, 2, 0, 0, 1);
        Collider c2 = new Collider(p);
        PlayerBehaviour behav2 = new PlayerBehaviour(go2);
        go2.insertElements(t2, c2, null, behav2);

        ge.addDisabled(go2);

        assertEquals(1, ge.disabled().size());
        assertEquals(1, ge.enabled().size());
    }

    @Test
    public void testDestroy(){
        GameEngine ge = GameEngine.getInstance();
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 3);
        pts[1] = new Point(4, 7);
        pts[2] = new Point(7, 7);
        pts[3] = new Point(7, 3);
        Polygon p = new Polygon(pts);
        GameObject go1 = new GameObject("Rect");
        Transform t1 = new Transform(2, 2, 0, 0, 1);
        Collider c1 = new Collider(p);
        PlayerBehaviour behav = new PlayerBehaviour(go1);
        go1.insertElements(t1, c1, null, behav);

        ge.addEnabled(go1);
        ge.destroy(go1);
        assertEquals(0, ge.enabled().size());

        ge.addDisabled(go1);
        ge.destroy(go1);
        assertEquals(0, ge.enabled().size());
    }
    
    @Test // TODO
    public void testGenerateNextFrameAndSimlateFrames(){
        GameEngine ge = GameEngine.getInstance();

        GameObject go1 = new GameObject("GO");
        Circle cir = new Circle(new Point(1, 2), 4d);
        Collider col = new Collider(cir);
        Transform tr = new Transform(3, 5, 0, 0, 0);
        PlayerBehaviour behav = new PlayerBehaviour(go1);
        Physics p = new Physics();
        p.setLayerSpeed(1);
        behav.setPhysics(p);

        GameObject go = new GameObject("circulo");
        go.insertElements(tr, col, null, behav);

        ge.addEnabled(go);
    }

    /*
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
        ge.addEnabled(go1);

        Point[] pts2 = new Point[4];
        pts2[0] = new Point(4, 3);
        pts2[1] = new Point(4, 7);
        pts2[2] = new Point(7, 7);
        pts2[3] = new Point(7, 3);
        Polygon rect = new Polygon(pts2);
        GameObject go2 = new GameObject("Rect", 5, 5, 0, 0, 1, rect);
        ge.addEnabled(go2);

        GameObject go3 = new GameObject("Circle", 10, 4, 0, 0, 1, new Circle(new Point(10, 4), 1d));        
        ge.addEnabled(go3);

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
        ge.addEnabled(go4);

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
    }*/



}
