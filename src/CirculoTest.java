
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CirculoTest{

    @Test
    public void testConstructor0(){
        assertDoesNotThrow(() -> {new Circulo(new Ponto(3,4), 3d);});
    }
    @Test
    public void testConstructor1(){
        assertThrows(IllegalArgumentException.class, () -> {new Circulo(new Ponto(3,4), 3.01d);});
    }


    @Test
    public void testToString0(){
        assertEquals(new Circulo(new Ponto(4,5), 3.060).toString(),
        "Circulo: (4,5) 3.06");
    }
    @Test
    public void testToString1(){
        assertEquals(new Circulo(new Ponto(4,5), 3.00).toString(),
        "Circulo: (4,5) 3");
    }


    @Test
    public void testTranslacao0(){
        assertEquals(new Circulo(new Ponto(4,5), 3.00).translacao(4,5).toString(),
        "Circulo: (8,10) 3");
    }
    @Test
    public void testTranslacao1(){
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                new Circulo(new Ponto(4,5), 3.00).translacao(-4,-6);
            }
        );
    }


    @Test
    public void testIntersetaSegmento0(){
        assertEquals(true,
            new Circulo(new Ponto(4,4), 4.)
            .intersetaSegmento(
                new Segmento(new Ponto(2,3), new Ponto(3, 3))
            )
        );
    }

    @Test
    public void testIntersetaSegmento1(){
        assertEquals(true,
            new Circulo(new Ponto(4,4), 4.)
            .intersetaSegmento(
                new Segmento(new Ponto(0, 3), new Ponto(4, 3))
            )
        );
    }


    @Test
    public void testcClisaoCirculo0(){
        assertEquals(true,
            new Circulo(new Ponto(4,4), 4.)
            .colisaoCirculo(
                new Circulo(new Ponto(4,4), 2.)
            )
        );
    }
    @Test
    public void testColisaoCirculo1(){
        assertEquals(false,
            new Circulo(new Ponto(4,4), 2.)
            .colisaoCirculo(
                new Circulo(new Ponto(8,8), 2.)
            )
        );
    }


    @Test
    public void TestColisaoPoligono0(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        assertEquals(true,
            new Circulo(new Ponto(4,4), 2.)
            .colisaoPoligono(
                new Poligono(pts)
            )
        );
    }

    @Test
    public void testColisaoPoligono1(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        assertEquals(true,
            new Circulo(new Ponto(3,6), 2.)
            .colisaoPoligono(
                new Poligono(pts)
            )
        );
    }
}
