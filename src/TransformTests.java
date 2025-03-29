import org.junit.Test;

import static org.junit.Assert.*;

public class TransformTests {
    @Test
    public void testTransformMethods() {
        Transform t = new Transform(1, 2, 1, 0, 1);
        assertTrue(t.position().equals(new Point(1, 2)));
        assertEquals(1, t.layer());
        assertEquals(0.0, t.angle(), 1e-9);
        assertEquals(1.0, t.scale(), 1e-9);

        t.move(new Point(2, 3), 2); // novo: pos=(3,5), layer=3
        assertTrue(t.position().equals(new Point(3, 5)));
        assertEquals(3, t.layer());

        t.rotate(45);
        assertEquals(45.0, t.angle(), 1e-9);
        t.rotate(320); // 45+320=365 mod360=5
        assertEquals(5.0, t.angle(), 1e-9);

        t.scale(2); // scale passa de 1 para 3
        assertEquals(3.0, t.scale(), 1e-9);

        String expected = "(3.00,5.00) 3 5.00 3.00";
        assertEquals(expected, t.toString());
    }
}
