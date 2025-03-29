import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PontoTest{
    
    @Test
    public void testConstructor0(){
        assertThrows(IllegalArgumentException.class, () -> {new Ponto(12, -1);});
    }
    @Test
    public void testConstructor1(){
        assertThrows(IllegalArgumentException.class, () -> {new Ponto(-5, 0);});
    }

    @Test
    public void testEquals0(){
        assertEquals( true, new Ponto(2,3).equals( new Ponto(2,3)));
    }
    @Test
    public void testEquals1(){
        assertEquals( false, new Ponto(3,2).equals( new Ponto(2,3)));
    }

    @Test
    public void testDistancia0(){
        assertEquals(new Ponto(3,2).distancia(new Ponto(0, 0)), Math.sqrt(3*3+2*2));
    }
    @Test
    public void testDistancia1(){
        assertEquals(new Ponto(8,10).distancia(new Ponto(5, 6)), 5);
    }

    @Test
    public void testTranslacao0(){
        assertDoesNotThrow(() -> {new Ponto(4, 5).translacao(-4,-5);});
    }
    @Test
    public void testTranslacao1(){
        assertThrows(IllegalArgumentException.class, () -> {new Ponto(3, 7).translacao(4,-8);});
    }

    @Test void testColinear0(){
        assertEquals(true, Ponto.colinear(
            new Ponto(4, 7),
            new Ponto(1, 2),
            new Ponto(7, 12)
        ));
    }
    @Test void testColinear1(){
        assertEquals(true, Ponto.colinear(
            new Ponto(6, 7),
            new Ponto(14, 15),
            new Ponto(101, 102)
        ));
    }
}
