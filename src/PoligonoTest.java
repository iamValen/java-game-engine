import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PoligonoTest{

    @Test
    public void testConstructor0(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(1, 1);
        pts[1] = new Ponto(2, 2);
        pts[2] = new Ponto(2, 1);
        pts[3] = new Ponto(1, 2);
        assertThrows(IllegalArgumentException.class, ()-> {new Poligono(pts);});
    }
    @Test
    public void testConstructor1(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(1, 1);
        pts[1] = new Ponto(2, 2);
        pts[2] = new Ponto(4, 4);
        pts[3] = new Ponto(3, 1);
        assertThrows(IllegalArgumentException.class, () -> {new Poligono(pts);});
    }


    @Test
    public void testTranslacao0(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        Poligono p = new Poligono(pts);
        assertThrows(IllegalArgumentException.class, () -> {p.translacao(-5, 3);});
    }
    @Test
    public void testTranslacao1(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        Poligono p = new Poligono(pts);
        assertEquals(p.toString(), p.translacao(5, 6).translacao(-5, -6).toString());
    }


    @Test
    public void testContemOPonto0(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        Poligono p = new Poligono(pts);
        assertEquals(false, p.contemOPonto(pts[0].translacao(3, 0)));
    }
    @Test
    public void testContemOPonto1(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        Poligono p = new Poligono(pts);
        assertEquals(false, p.contemOPonto(pts[0].translacao(3, 0)));
    }


    @Test
    public void testColisaoCirculo0(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        assertEquals(true,
        new Poligono(pts)
            .colisaoCirculo(
                new Circulo(new Ponto(4,4), 2.)
            )
        );
    }
    @Test
    public void testColisaoCirculo1(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        assertEquals(true,
        new Poligono(pts)
            .colisaoCirculo(
                new Circulo(new Ponto(3,6), 2.)
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
        Poligono p1 = new Poligono(pts);
        assertEquals(false, p1.colisaoPoligono(p1.translacao(4, 2)));
    }
    @Test
    public void testColisaoPoligono1(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        Poligono p1 = new Poligono(pts);
        pts[0] = new Ponto(5, 9);
        pts[1] = new Ponto(7, 9);
        pts[2] = new Ponto(7, 3);
        pts[3] = new Ponto(5, 3);
        Poligono p2 = new Poligono(pts);
        assertEquals(true, p1.colisaoPoligono(p2));
    }


    @Test
    public void testToString0(){
        Ponto[] pts = new Ponto[4];
        pts[0] = new Ponto(4, 4);
        pts[1] = new Ponto(4, 8);
        pts[2] = new Ponto(8, 8);
        pts[3] = new Ponto(8, 4);
        Poligono p1 = new Poligono(pts);
        assertEquals("Poligono de 4 vertices: [(4,4), (4,8), (8,8), (8,4)]", p1.toString());
    }
    @Test
    public void testToString1(){
        Ponto[] pts = new Ponto[3];
        pts[0] = new Ponto(50, 70);
        pts[1] = new Ponto(2, 8);
        pts[2] = new Ponto(30, 1);
        Poligono p1 = new Poligono(pts);
        assertEquals("Poligono de 3 vertices: [(50,70), (2,8), (30,1)]", p1.toString());
    }
}
