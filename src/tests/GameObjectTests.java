package tests;
import org.junit.Test;

import engine.Transform;
import engine.Collider;
import engine.GameObject;
import figures.Circle;
import figures.Point;
import figures.Polygon;
import game.PlayerBehaviour;

import static org.junit.Assert.*;

public class GameObjectTests {
    @Test
    public void testGameObjectWithCirculo() {
        Circle circle = new Circle(new Point(2, 2), 3.0);
        GameObject go1 = new GameObject("Alien01");
        Transform t1 = new Transform(1, 2, 1, 0, 1);
        Collider c1 = new Collider(circle);
        go1.insertElements(t1, c1, null, new PlayerBehaviour(1,1));
        assertEquals("Alien01", go1.name());
        assertEquals("(1.00,2.00) 1 0.00 1.00", go1.transform().toString());
        assertEquals("(1.00,2.00) 3.00", go1.collider().toString());

        circle = new Circle(new Point(1, 2), 3.0);
        GameObject go2 = new GameObject("Alien02");
        t1 = new Transform(3, 7, 2, 45.6, 2);
        c1 = new Collider(circle);
        go2.insertElements(t1, c1, null, new PlayerBehaviour(1,1));
        assertEquals("Alien02", go2.name());
        assertEquals("(3.00,7.00) 2 45.60 2.00", go2.transform().toString());
        // Com escala 2, raio: 3*2 = 6.00; centro ajustado para (3,7)
        assertEquals("(3.00,7.00) 6.00", go2.collider().toString());
    }

    @Test
    public void testGameObjectWithPoligono() {
        Point[] pts = new Point[] { new Point(2,2), new Point(2,6), new Point(4,6), new Point(4,2)};
        Polygon poly = new Polygon(pts);
        GameObject go1 = new GameObject("PlayerOne");
        Transform t2 = new Transform(5, 9, 0, 90, 2);
        Collider c2 = new Collider(poly);
        go1.insertElements(t2, c2, null, new PlayerBehaviour(1,1));
        assertEquals("PlayerOne", go1.name());
        assertEquals("(5.00,9.00) 0 90.00 2.00", go1.transform().toString());
        String expected = "(9.00,7.00) (1.00,7.00) (1.00,11.00) (9.00,11.00)";
        assertEquals(expected, go1.collider().toString());
    }
}
