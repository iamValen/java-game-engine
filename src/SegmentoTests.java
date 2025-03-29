import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SegmentoTests {

    @Test
    public void testConstructor0(){
        assertThrows(IllegalArgumentException.class, () -> {
                new Segmento(
                    new Point(1, 3),
                    new Point(1,3)
                );
            }
        );
    }
    @Test
    public void testConstructor1(){
        assertDoesNotThrow(() -> {
                new Segmento(
                    new Point(0, 0),
                    new Point(1,0)
                );
            }
        );
    }


    @Test
    public void testIntersecao0(){
        assertEquals(false,
            new Segmento(new Point(1, 0), new Point(1,2))
            .intersecao(
                new Segmento(new Point(3, 1), new Point(5,1))
            )
        );
    }
    @Test
    public void testIntersecao1(){
        assertEquals(false,
            new Segmento(new Point(1, 1), new Point(1,2))
            .intersecao(
                new Segmento(new Point(1, 3), new Point(1,4))
            )
        );
    }
}
