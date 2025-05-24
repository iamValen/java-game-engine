package test;

import org.junit.Test;

import engine.Collider;
import engine.Transform;
import figures.Circle;
import figures.Point;
import figures.Polygon;

import static org.junit.Assert.*;

public class ColliderTests {
    @Test
    public void testColliderWithCirculo() {
        Transform tCircle = new Transform(10, 20, 2, 90, 1);
        Circle circle = new Circle(new Point(1, 1), 3.0);
        Collider colCircle = new Collider(circle);
        colCircle.ITransform(tCircle);
        colCircle.onUpdate();//setTransform(tCircle);
        String expected = "(10.00,20.00) 3.00";
        assertEquals(expected, colCircle.toString());
    }

    @Test
    public void testColliderWithPoligono() {
        Point[] pts = new Point[] {
            new Point(1,1), new Point(1,3),
            new Point(3,3), new Point(3,1)
        };
        Polygon pol = new Polygon(pts);
        Transform tPoly = new Transform(50, 60, 0, 0, 1);
        Collider colPoly = new Collider(pol);
        colPoly.ITransform(tPoly);
        colPoly.onUpdate();
        String expected = "(49.00,59.00) (49.00,61.00) (51.00,61.00) (51.00,59.00)";
        assertEquals(expected, colPoly.toString());
    }
}