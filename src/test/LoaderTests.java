package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import engine.GameEngine;
import gui.Loader;
import interfaces.IGameObject;

public class LoaderTests {

    private GameEngine engine;

    @BeforeEach
    public void setUp() {
        // Sempre partir de um motor vazio
        engine = GameEngine.getInstance();
        engine.destroyAll();
    }

    @Test
    public void testLoadLevel1PopulatesCorrectObjects() {
        engine.destroyAll();
        Loader.loadLevel(1, 1);
        engine.simulateFrames(4);
        // Após carregar level1, devemos ter exatamente estes objectos em enabled:
        //   Player, Enemy1, loading_screen, floor, block, healthHUD
        Set<String> names = engine.enabled().stream()
        .map(IGameObject::name)
        .collect(Collectors.toSet());
        
        assertEquals(3, names.size(), "Level1 deve criar 6 GameObjects ativos");
        System.out.println(names);
        assertTrue(names.contains("Player"),      "Level1 deve conter 'Player'");
        assertTrue(names.contains("Enemy1"),      "Level1 deve conter 'Enemy1'");
        assertTrue(names.contains("loading_screen"), "Level1 deve conter 'loading_screen'");
    }

    @Test
    public void testLoadNonexistentLevelDoesNotThrow() {
        // Carregar nível que não existe deve apenas loggar, não lançar
        assertDoesNotThrow(() -> Loader.loadLevel(99, 0),
            "loadLevel de nível inexistente não deve lançar exceções");
        // E não deve criar objectos
        assertTrue(engine.enabled().isEmpty(),
            "Após tentar carregar nível inexistente, não deve haver objectos ativos");
    }
}
