package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import figures.Circle;
import figures.Point;
import figures.Polygon;
import figures.Segment;

public class CircleTests{

    @Test
    public void testConstructor0(){
        assertDoesNotThrow(() -> {new Circle(new Point(3,4), 3d);});
    }
    /*@Test
    public void testConstructor1(){
        assertThrows(IllegalArgumentException.class, () -> {new Circle(new Point(3,4), 3.01d);});
    }*/


    @Test
    public void testToString0(){
        assertEquals(new Circle(new Point(4,5), 3.060).toString(),
        "(4.00,5.00) 3.06");
    }
    @Test
    public void testToString1(){
        assertEquals(new Circle(new Point(4,5), 3.00).toString(),
        "(4.00,5.00) 3.00");
    }


    @Test
    public void testTranslacao0(){
        assertEquals(new Circle(new Point(4,5), 3.00).translation(4,5).toString(),
        "(8.00,10.00) 3.00");
    }
    /*@Test
    public void testTranslacao1(){
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                new Circle(new Point(4,5), 3.00).translation(-4,-6);
            }
        );
    }*/


    @Test
    public void testIntersetaSegmento0(){
        assertEquals(true,
            new Circle(new Point(4,4), 4.)
            .intersetaSegmento(
                new Segment(new Point(2,3), new Point(3, 3))
            )
        );
    }

    @Test
    public void testIntersetaSegmento1(){
        assertEquals(true,
            new Circle(new Point(4,4), 4.)
            .intersetaSegmento(
                new Segment(new Point(0, 3), new Point(4, 3))
            )
        );
    }


    @Test
    public void testcClisaoCirculo0(){
        assertEquals(true,
            new Circle(new Point(4,4), 4.)
            .colisaoCirculo(
                new Circle(new Point(4,4), 2.)
            )
        );
    }
    @Test
    public void testColisaoCirculo1(){
        assertEquals(false,
            new Circle(new Point(4,4), 2.)
            .colisaoCirculo(
                new Circle(new Point(8,8), 2.)
            )
        );
    }


    @Test
    public void TestColisaoPoligono0(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        assertEquals(true,
            new Circle(new Point(4,4), 2.)
            .collisionPolygon(
                new Polygon(pts)
            )
        );
    }

    @Test
    public void testColisaoPoligono1(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        assertEquals(true,
            new Circle(new Point(3,6), 2.)
            .collisionPolygon(
                new Polygon(pts)
            )
        );
    }
}
