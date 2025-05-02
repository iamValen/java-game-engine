package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import figures.Point;

public class PointTests{

    /*@Test
    public void testConstructor0(){
        assertThrows(IllegalArgumentException.class, () -> {new Point(12, -1);});
    }
    @Test
    public void testConstructor1(){
        assertThrows(IllegalArgumentException.class, () -> {new Point(-5, 0);});
    }*/

    @Test
    public void testEquals0(){
        assertEquals( true, new Point(2,3).equals( new Point(2,3)));
    }
    @Test
    public void testEquals1(){
        assertEquals( false, new Point(3,2).equals( new Point(2,3)));
    }

    @Test
    public void testDistancia0(){
        assertEquals(new Point(3,2).distance(new Point(0, 0)), Math.sqrt(3*3+2*2));
    }
    @Test
    public void testDistancia1(){
        assertEquals(new Point(8,10).distance(new Point(5, 6)), 5);
    }

    @Test
    public void testTranslacao0(){
        assertDoesNotThrow(() -> {new Point(4, 5).translacao(-4,-5);});
    }
    
    /*@Test
    public void testTranslacao1(){
        assertThrows(IllegalArgumentException.class, () -> {new Point(3, 7).translacao(4,-8);});
    }*/

    @Test void testColinear0(){
        assertEquals(true, Point.collinear(
            new Point(4, 7),
            new Point(1, 2),
            new Point(7, 12)
        ));
    }
    @Test void testColinear1(){
        assertEquals(true, Point.collinear(
            new Point(6, 7),
            new Point(14, 15),
            new Point(101, 102)
        ));
    }
}
