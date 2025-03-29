import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SegmentoTest {
    
    @Test
    public void testConstructor0(){
        assertThrows(IllegalArgumentException.class, () -> {
                new Segmento(
                    new Ponto(1, 3),
                    new Ponto(1,3)
                );
            }
        );
    }
    @Test
    public void testConstructor1(){
        assertDoesNotThrow(() -> {
                new Segmento(
                    new Ponto(0, 0),
                    new Ponto(1,0)
                );
            }
        );
    }


    @Test
    public void testIntersecao0(){
        assertEquals(false,
            new Segmento(new Ponto(1, 0), new Ponto(1,2))
            .intersecao(
                new Segmento(new Ponto(3, 1), new Ponto(5,1))
            )
        );
    }
    @Test
    public void testIntersecao1(){
        assertEquals(false,
            new Segmento(new Ponto(1, 1), new Ponto(1,2))
            .intersecao(
                new Segmento(new Ponto(1, 3), new Ponto(1,4))
            )
        );
    }
}
