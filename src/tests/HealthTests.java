package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import engine.Collider;
import engine.GameObject;
import engine.GameEngine;
import engine.Transform;
import game.AAtack;
import game.Health;
import game.MeleeAttackBehaviour;
import geometry.Circle;
import geometry.Point;
import interfaces.IShape;
import interfaces.IGameObject;

public class HealthTests {

    private GameEngine engine;
    private GameObject go;
    private Health health;

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
            @Override public void render(Graphics g, int x, int y) {}
        };
        go.insertElements(t, c, shape, null);

        // Instancia a Entity com 10 de vida
        health = new Health(go, 10);
        engine.addEnabled(go);
    }

    @Test
    public void testInitialHealth() {
        assertEquals(10, health.getHealth(),
            "getHealth() deve devolver o valor inicial");
    }


    @Test
    public void testDamageWithinCooldownIsIgnored() {
        AAtack atkbhav = new MeleeAttackBehaviour(null, 4, 0, null);
        IGameObject atk = new GameObject(null);
        atk.insertElements(null, null, null, atkbhav);
        health.takeDamage(atk);
        assertEquals(6, health.getHealth());
        health.takeDamage(atk); // dentro de 2s não deve aplicar
        assertEquals(6, health.getHealth(),
            "Dano dentro do cooldown de 2s deve ser ignorado");
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException IE){}
        health.takeDamage(atk); // passado 2s deve aplicar
        assertEquals(2, health.getHealth());

    }

    /*
     * acho q isto e inutil agora
     */
    // @Test
    // public void testCreateAndDisableAttack() throws Exception {
    //     // Cria um projétil de ataque como GameObject vazio
    //     GameObject attack = new GameObject("attack");
    //     Transform tAtk = new Transform(100, 100, 0, 0, 1);
    //     Collider cAtk = new Collider(new Circle(new Point(100,100), 5.0));
    //     attack.insertElements(tAtk, cAtk, null, null);
    //     engine.addDisabled(attack);

    //     long now = System.currentTimeMillis();
    //     // createAttack deve ativar o ataque
    //     entity.createAttack(attack, now - 1000);
    //     assertFalse(engine.isEnabled(attack),
    //         "createAttack fora do cooldown deve ativar o ataque");

    //     // disableAttack deve desativar após 60ms
    //     Thread.sleep(70);
    //     entity.disableAttack(attack, now);
    //     assertFalse(engine.isEnabled(attack),
    //         "disableAttack após 60ms deve desativar o ataque");
    // }
}
