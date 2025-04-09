import org.junit.Test;

import static org.junit.Assert.*;

public class GameObjectTests {
    @Test
    public void testGameObjectWithCirculo() {
        Circle circle = new Circle(new Point(2, 2), 3.0);
        GameObject go1 = new GameObject("Alien01", 1, 2, 1, 0, 1, circle);
        assertEquals("Alien01", go1.name());
        assertEquals("(1.00,2.00) 1 0.00 1.00", go1.transform().toString());
        assertEquals("(1.00,2.00) 3.00", go1.collider().toString());

        circle = new Circle(new Point(1, 2), 3.0);
        GameObject go = new GameObject("Alien02", 3, 7, 2, 45.6, 2, circle);
        assertEquals("Alien02", go.name());
        assertEquals("(3.00,7.00) 2 45.60 2.00", go.transform().toString());
        // Com escala 2, raio: 3*2 = 6.00; centro ajustado para (3,7)
        assertEquals("(3.00,7.00) 6.00", go.collider().toString());
    }

    @Test
    public void testGameObjectWithPoligono() {
        Point[] pts = new Point[] {
            new Point(2,2), new Point(2,6),
            new Point(4,6), new Point(4,2)
        };
        Polygon poly = new Polygon(pts);
        GameObject go2 = new GameObject("PlayerOne", 5, 9, 0, 90, 2, poly);
        assertEquals("PlayerOne", go2.name());
        assertEquals("(5.00,9.00) 0 90.00 2.00", go2.transform().toString());
        String expected = "(9.00,7.00) (1.00,7.00) (1.00,11.00) (9.00,11.00)";
        assertEquals(expected, go2.collider().toString());
    }
}
