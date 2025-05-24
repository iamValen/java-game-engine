package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import behaviour.Physics;
import figures.Point;

public class PhysicsTests {

    private Physics physics;
    private static final double EPS = 1e-6;

    @BeforeEach
    public void setUp() {
        physics = new Physics();
    }

    @Test
    public void testInitialState() {
        // Velocidade inicial a zero
        Point v0 = physics.Speed();
        assertEquals(0.0, v0.x(), EPS, "Velocidade x inicial deve ser 0");
        assertEquals(0.0, v0.y(), EPS, "Velocidade y inicial deve ser 0");
        // Aceleração inicial igual à gravidade (0,160)
        Point a0 = physics.Accel();
        assertEquals(0.0, a0.x(), EPS, "Aceleração x inicial deve ser 0");
        assertEquals(160.0, a0.y(), EPS, "Aceleração y inicial deve ser 160");
    }

    @Test
    public void testSetAndSumAccel() {
        physics.setAccel(50, 100);
        Point a1 = physics.Accel();
        assertEquals(50.0, a1.x(), EPS, "setAccel deve definir aceleração x");
        assertEquals(100.0, a1.y(), EPS, "setAccel deve definir aceleração y");

        physics.sumAccel(-20, 40);
        Point a2 = physics.Accel();
        assertEquals(30.0, a2.x(), EPS, "sumAccel deve acumular aceleração x");
        assertEquals(140.0, a2.y(), EPS, "sumAccel deve acumular aceleração y");
    }

    @Test
    public void testSetSpeed() {
        physics.setSpeed(5, -10);
        Point v = physics.Speed();
        assertEquals(5.0, v.x(), EPS, "setSpeed deve definir velocidade x");
        assertEquals(-10.0, v.y(), EPS, "setSpeed deve definir velocidade y");
    }

    @Test
    public void testUpdateIntegratesAccelAndAppliesFrictionAndResetsAccel() {
        // Define velocidade e aceleração para um caso determinístico
        physics.setSpeed(10, 0);
        physics.setAccel(0, 200); // (0,200)
        double dt = 0.1; // 100ms

        // Manual: v' = (10,0) + (0,200)*0.1 = (10,20)
        // frictionFactor = 0.85^(dT/0.016666) = 
        // v''.x = 10 * 0.141666... = 1.41666..., v''.y = 20 (unchanged by horizontal friction)
        physics.update(dt);
        Point vAfter = physics.Speed();
        double frictionFactor = Math.pow(0.85 , dt/0.016666);
        assertEquals(10 * frictionFactor, vAfter.x(), EPS, "Velocidade x após update incorreta");

        // Após update, accel deve ter sido reposto para (0,160)
        Point aAfter = physics.Accel();
        assertEquals(0.0, aAfter.x(), EPS, "Aceleração x após update deve ser 0 (gravidade)");
        assertEquals(160.0, aAfter.y(), EPS, "Aceleração y após update deve ser 160 (gravidade)");
    }

    @Test
    public void testUnusedGettersReturnDefaults() {
        // layerSpeed, rotationSpeed e scaleSpeed não são alterados
        assertEquals(0, physics.LayerSpeed(), "LayerSpeed inicial deve ser 0");
        assertEquals(0.0, physics.RotationSpeed(), EPS, "RotationSpeed inicial deve ser 0");
        assertEquals(0.0, physics.ScaleSpeed(), EPS, "ScaleSpeed inicial deve ser 0");
    }
}
