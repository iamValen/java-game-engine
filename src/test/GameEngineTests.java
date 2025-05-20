package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import behaviour.AAABehaviour;
import behaviour.Physics;
import engine.Collider;
import engine.GameEngine;
import engine.GameObject;
import engine.Transform;
import figures.Circle;
import figures.Point;
import figures.Polygon;
import interfaces.IGameObject;

public class GameEngineTests {

    class testBehaviour extends AAABehaviour{
        Physics physics;

        boolean onInitRan = false;
        boolean onEnableRan = false;
        boolean onDisableRan = false;
        boolean onDestroyRan = false;
        boolean onCollision = false;

        public boolean collision(){
            return onCollision;
        }

        public void onCollision(ArrayList<IGameObject> gol){onCollision = true;}
        public void oninit(){onInitRan = true;}
        public void onEnable(){onEnableRan = true;}
        public void onDisable(){onDisableRan = true;}
        public void onDestroy(){onDestroyRan = true;}
        
        public void setPhysics(Physics p){ this.physics = p; }
    }

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
        testBehaviour behav = new testBehaviour();
        go1.insertElements(t1, c1, null, behav);

        ge.addEnabled(go1);
        ge.simulateFrames(1);
        assertEquals(1, ge.enabled().size());
        assertEquals(0, ge.disabled().size());

        GameObject go2 = new GameObject("Rect");
        Transform t2 = new Transform(2, 2, 0, 0, 1);
        Collider c2 = new Collider(p);
        testBehaviour behav2 = new testBehaviour();
        go2.insertElements(t2, c2, null, behav2);

        ge.addDisabled(go2);
        ge.simulateFrames(1);
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
        testBehaviour behav = new testBehaviour();
        go1.insertElements(t1, c1, null, behav);

        ge.addEnabled(go1);
        ge.destroy(go1);
        assertEquals(false, ge.enabled().contains(go1));

        ge.addDisabled(go1);
        ge.destroy(go1);
        assertEquals(false, ge.enabled().contains(go1));
    }
    
    
    @Test
    public void testDetectCollisions(){
        GameEngine ge = GameEngine.getInstance();
        // Collides
        ge.destroyAll();
        ge.simulateFrames(1);
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square = new Polygon(pts1);
        GameObject go1 = new GameObject("Square");
        Transform t1 = new Transform(2, 2, 0, 0, 1);
        Collider c1 = new Collider(square);
        testBehaviour behav = new testBehaviour();
        go1.insertElements(t1, c1, null, behav);
        ge.addEnabled(go1);

        Point[] pts2 = new Point[4];
        pts2[0] = new Point(4, 3);
        pts2[1] = new Point(4, 7);
        pts2[2] = new Point(7, 7);
        pts2[3] = new Point(7, 3);
        Polygon rect = new Polygon(pts2);
        GameObject go2 = new GameObject("Rect");
        Transform t2 = new Transform(2, 2, 0, 0, 1);
        Collider c2 = new Collider(rect);
        testBehaviour behav2 = new testBehaviour();
        go2.insertElements(t2, c2, null, behav2);
        ge.addEnabled(go2);

        Circle circle = new Circle(new Point(10, 4), 1d);
        GameObject go3 = new GameObject("Circle");
        Transform t3 = new Transform(10, 4, 0, 0, 1);
        Collider c3 = new Collider(circle);
        testBehaviour behav3 = new testBehaviour();
        go3.insertElements(t3, c3, null, behav3);
        ge.addEnabled(go3);

        ge.checkCollisions();
        ge.simulateFrames(1);
        /* 
        assertEquals(true, ((testBehaviour)(ge.enabled().get(0).behaviour())).collision());
        assertEquals(true, ((testBehaviour)(ge.enabled().get(1).behaviour())).collision());
        assertEquals(false, ((testBehaviour)(ge.enabled().get(2).behaviour())).collision());
        */
        ge.destroyAll();
        
        // Don't collide
        GameObject go4 = new GameObject("bullet");  
        Transform t4 = new Transform(1.5, 2.5, 4, 0, 1);
        Collider c4 = new Collider(new Circle(new Point(1.5, 2.5), 1d));
        testBehaviour behav4 = new testBehaviour();
        go4.insertElements(t4, c4, null, behav4);      
        ge.addEnabled(go4);

        Point[] pts3 = new Point[4];
        pts3[0] = new Point(8, 1);
        pts3[1] = new Point(8, 4);
        pts3[2] = new Point(9, 4);
        pts3[3] = new Point(9, 1);
        Polygon target = new Polygon(pts2);
        GameObject go5 = new GameObject("target");
        Transform t5 = new Transform(8.5, 2.5, 4, 0, 1);
        Collider c5 = new Collider(target);
        testBehaviour behav5 = new testBehaviour();
        go5.insertElements(t5, c5, null, behav5);

        ge.addEnabled(go5);
        ge.checkCollisions();
        //assertEquals(false, ((testBehaviour)(ge.enabled().get(0).behaviour())).collision());
        //assertEquals(false, ((testBehaviour)(ge.enabled().get(1).behaviour())).collision());

    }

    @Test
    public void testDisable(){
        GameEngine ge = GameEngine.getInstance();
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square = new Polygon(pts1);
        GameObject go1 = new GameObject("Square");
        Transform t1 = new Transform(2, 2, 0, 0, 1);
        Collider c1 = new Collider(square);
        testBehaviour behav = new testBehaviour();
        go1.insertElements(t1, c1, null, behav);
        ge.addEnabled(go1);
        ge.disable(go1);
        /*assertEquals(true, ge.isDisabled(go1));
        assertEquals(false, ge.enabled().contains(go1));
        assertEquals(true, ge.disabled().contains(go1));*/
    }


    @Test
    public void testEnable(){
        GameEngine ge = GameEngine.getInstance();
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square = new Polygon(pts1);
        GameObject go1 = new GameObject("Square");
        Transform t1 = new Transform(2, 2, 0, 0, 1);
        Collider c1 = new Collider(square);
        testBehaviour behav = new testBehaviour();
        go1.insertElements(t1, c1, null, behav);
        ge.addDisabled(go1);
        ge.enable(go1);
        ge.simulateFrames(1);
        assertEquals(true, ge.isEnabled(go1));
        assertEquals(true, ge.enabled().contains(go1));
        ge.simulateFrames(1);
        //assertEquals(false, ge.disabled().contains(go1));
    }


    @Test
    public void testDestroyAll(){
        GameEngine ge = GameEngine.getInstance();
        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        Polygon square1 = new Polygon(pts1);
        GameObject go1 = new GameObject("Square");
        Transform t1 = new Transform(2, 2, 0, 0, 1);
        Collider c1 = new Collider(square1);
        testBehaviour behav1 = new testBehaviour();
        go1.insertElements(t1, c1, null, behav1);

        Point[] pts2 = new Point[4];
        pts2[0] = new Point(4, 3);
        pts2[1] = new Point(4, 7);
        pts2[2] = new Point(7, 7);
        pts2[3] = new Point(7, 3);
        Polygon square2 = new Polygon(pts2);
        GameObject go2 = new GameObject("Rect");
        Transform t2 = new Transform(2, 2, 0, 0, 1);
        Collider c2 = new Collider(square2);
        testBehaviour behav2 = new testBehaviour();
        go2.insertElements(t2, c2, null, behav2);

        ge.addEnabled(go1);
        ge.addEnabled(go2);

        ge.simulateFrames(1);
        assertEquals(true, ge.enabled().contains(go1));
        assertEquals(true, ge.enabled().contains(go2));

        ge.destroyAll();
        ge.simulateFrames(1);
        assertEquals(false, ge.enabled().contains(go1));
        assertEquals(false, ge.enabled().contains(go2));
        assertEquals(0, ge.enabled().size());
        assertEquals(0, ge.disabled().size());
    }
}
