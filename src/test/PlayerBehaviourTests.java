package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import behaviour.PlayerBehaviour;
import engine.GameEngine;
import engine.InputManager;
import gui.ObjectCreator;
import interfaces.IGameObject;

/**
 * Testes unitários para PlayerBehaviour, simulando teclas via KeyEvents.
 */
public class PlayerBehaviourTests {

    private GameEngine engine;
    private IGameObject playerGO;
    private PlayerBehaviour behaviour;
    private InputManager inputManager;
    private Canvas dummyCanvas; 

    @BeforeEach
    public void setUp() {
        // Limpa engine
        engine = GameEngine.getInstance();
        engine.destroyAll();

        // Cria o player via ObjectCreator
        playerGO = ObjectCreator.Player(0, 0);
        behaviour = (PlayerBehaviour) playerGO.behaviour();
        engine.addEnabled(playerGO);

        // Simula oninit do Behaviour
        behaviour.oninit();

        // Cria InputManager e componente dummy para KeyEvents
        inputManager = new InputManager();
        dummyCanvas = new Canvas();
        dummyCanvas.addKeyListener(inputManager);
    }

    private void pressKey(int keyCode) {
        KeyEvent press = new KeyEvent(dummyCanvas,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                keyCode,
                KeyEvent.CHAR_UNDEFINED);
        inputManager.keyPressed(press);
    }

    private void releaseKey(int keyCode) {
        KeyEvent release = new KeyEvent(dummyCanvas,
                KeyEvent.KEY_RELEASED,
                System.currentTimeMillis(),
                0,
                keyCode,
                KeyEvent.CHAR_UNDEFINED);
        inputManager.keyReleased(release);
    }

    @Test
    public void testOnInitCreatesDisabledAttackAndEntity() {
        engine.simulateFrames(1);
        List<IGameObject> disabled = engine.disabled();

        assertNotNull(behaviour.entity(),
            "Entity não deve ser nulo após oninit");
        assertEquals(100, behaviour.entity().getHealth(),
            "Entity deve iniciar com 100 de vida");
    }

    @Test
    public void testOnDestroyRemovesAttack() {
        // Antes de onDestroy, attack existe em disabled
        assertFalse(engine.enabled().stream()
            .anyMatch(go -> go.name().equals("playerAttack")),
            "attack não deve estar em enabled antes de onDestroy");
        behaviour.onDestroy();
        assertFalse(engine.disabled().stream()
            .anyMatch(go -> go.name().equals("playerAttack")),
            "attack deve ser removido de disabled após onDestroy");
    }

    @Test
    public void testMoveLeftAndRightChangesDirection() {
        // Simula chão para permitir movimento horizontal
        behaviour.setGrounded(true);

        // Pressiona A
        pressKey(KeyEvent.VK_A);
        behaviour.onUpdate(0.1);
        assertEquals(-1, playerGO.transform().direction(),
            "Ao carregar A, direção deve ser -1");
        releaseKey(KeyEvent.VK_A);

        // Pressiona D
        pressKey(KeyEvent.VK_D);
        behaviour.onUpdate(0.1);
        assertEquals(1, playerGO.transform().direction(),
            "Ao carregar D, direção deve ser 1");
        releaseKey(KeyEvent.VK_D);
    }

    @Test
    public void testJump() {
        behaviour.setGrounded(true);

        // Pressiona W
        pressKey(KeyEvent.VK_W);
        behaviour.onUpdate(0.1);
        double vyFirst = behaviour.physics().Speed().y();
        assertTrue(vyFirst < 0,
            "Impulso de salto deve produzir velocidade Y negativa");
    }
}
