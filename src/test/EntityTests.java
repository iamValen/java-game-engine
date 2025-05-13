package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import behaviour.Entity;
import engine.Collider;
import engine.GameObject;
import engine.GameEngine;
import engine.Transform;
import figures.Circle;
import figures.Point;
import interfaces.IShape;
import interfaces.ITransform;
import interfaces.IGameObject;

public class EntityTests {

    private GameEngine engine;
    private GameObject go;
    private Entity entity;

    @BeforeEach
    public void setUp() {
        // Garante que o motor está limpo antes de cada teste
        engine = GameEngine.getInstance();
        engine.destroyAll();

        // Cria um GameObject simples com Circle e um Shape de teste
        go = new GameObject("TestEntity");
        Transform t = new Transform(0, 0, 0, 0, 1);
        Collider c = new Collider(new Circle(new Point(0,0), 10.0));
        // Stub de IShape que só guarda a cor
        IShape shape = new IShape() {
            Color lastColor;
            @Override public void render(Graphics g, int x, int y) {}
            @Override public void setColor(Color color) { lastColor = color; }
            public Color getLastColor() { return lastColor; }
        };
        go.insertElements(t, c, shape, null);

        // Instancia a Entity com 10 de vida
        entity = new Entity(go, 10);
        engine.addEnabled(go);
    }

    @Test
    public void testInitialHealth() {
        assertEquals(10, entity.getHealth(),
            "getHealth() deve devolver o valor inicial");
    }

    @Test
    public void testDamageWithinCooldownIsIgnored() {
        entity.damage(5);
        assertEquals(5, entity.getHealth());
        entity.damage(3); // dentro de 2s não deve aplicar
        assertEquals(5, entity.getHealth(),
            "Dano dentro do cooldown de 2s deve ser ignorado");
    }

    @Test
    public void testCreateAndDisableAttack() throws Exception {
        // Cria um projétil de ataque como GameObject vazio
        GameObject attack = new GameObject("attack");
        Transform tAtk = new Transform(100, 100, 0, 0, 1);
        Collider cAtk = new Collider(new Circle(new Point(100,100), 5.0));
        attack.insertElements(tAtk, cAtk, null, null);
        engine.addDisabled(attack);

        long now = System.currentTimeMillis();
        // createAttack deve ativar o ataque
        entity.createAttack(attack, now - 1000);
        assertFalse(engine.isEnabled(attack),
            "createAttack fora do cooldown deve ativar o ataque");

        // disableAttack deve desativar após 60ms
        Thread.sleep(70);
        entity.disableAttack(attack, now);
        assertFalse(engine.isEnabled(attack),
            "disableAttack após 60ms deve desativar o ataque");
    }
}
