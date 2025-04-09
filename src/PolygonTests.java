import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PolygonTests{

    @Test
    public void testConstructor0(){
        Point[] pts = new Point[4];
        pts[0] = new Point(1, 1);
        pts[1] = new Point(2, 2);
        pts[2] = new Point(2, 1);
        pts[3] = new Point(1, 2);
        assertThrows(IllegalArgumentException.class, ()-> {new Polygon(pts);});
    }
    @Test
    public void testConstructor1(){
        Point[] pts = new Point[4];
        pts[0] = new Point(1, 1);
        pts[1] = new Point(2, 2);
        pts[2] = new Point(4, 4);
        pts[3] = new Point(3, 1);
        assertThrows(IllegalArgumentException.class, () -> {new Polygon(pts);});
    }


    @Test
    public void testTranslacao0(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        Polygon p = new Polygon(pts);
        assertThrows(IllegalArgumentException.class, () -> {p.translation(-5, 3);});
    }
    @Test
    public void testTranslacao1(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        Polygon p = new Polygon(pts);
        assertEquals(p.toString(), p.translation(5, 6).translation(-5, -6).toString());
    }


    @Test
    public void testContemOPoint0(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        Polygon p = new Polygon(pts);
        assertEquals(false, p.containsPoint(pts[0].translacao(3, 0)));
    }
    @Test
    public void testContemOPoint1(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        Polygon p = new Polygon(pts);
        assertEquals(false, p.containsPoint(pts[0].translacao(3, 0)));
    }


    @Test
    public void testColisaoCirculo0(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        assertEquals(true,
        new Polygon(pts)
            .collisionCircle(
                new Circle(new Point(4,4), 2.)
            )
        );
    }
    @Test
    public void testColisaoCirculo1(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        assertEquals(true,
        new Polygon(pts)
            .collisionCircle(
                new Circle(new Point(3,6), 2.)
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
        Polygon p1 = new Polygon(pts);
        assertEquals(false, p1.collisionPolygon(p1.translation(4, 2)));
    }
    @Test
    public void testColisaoPoligono1(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        Polygon p1 = new Polygon(pts);
        pts[0] = new Point(5, 9);
        pts[1] = new Point(7, 9);
        pts[2] = new Point(7, 3);
        pts[3] = new Point(5, 3);
        Polygon p2 = new Polygon(pts);
        assertEquals(true, p1.collisionPolygon(p2));
    }


    @Test
    public void testToString0(){
        Point[] pts = new Point[4];
        pts[0] = new Point(4, 4);
        pts[1] = new Point(4, 8);
        pts[2] = new Point(8, 8);
        pts[3] = new Point(8, 4);
        Polygon p1 = new Polygon(pts);
        assertEquals("Poligono de 4 vertices: [(4,4), (4,8), (8,8), (8,4)]", p1.toString());
    }
    @Test
    public void testToString1(){
        Point[] pts = new Point[3];
        pts[0] = new Point(50, 70);
        pts[1] = new Point(2, 8);
        pts[2] = new Point(30, 1);
        Polygon p1 = new Polygon(pts);
        assertEquals("Poligono de 3 vertices: [(50,70), (2,8), (30,1)]", p1.toString());
    }
}
