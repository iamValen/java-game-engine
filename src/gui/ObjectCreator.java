package gui;

import behaviour.*;
import engine.Collider;
import engine.GameEngine;
import engine.GameObject;
import engine.Transform;
import figures.Circle;
import figures.Point;
import figures.Polygon;
import interfaces.*;
import shapes.BlockShape;
import shapes.HUDHealthBarShape;
import shapes.TestShape;

/**
 * Classe responsável por criar e configurar todos os GameObjects do jogo.
 * Cada método estático gera um tipo específico de objeto (jogador, inimigo,
 * chão, bloco, HUD, etc.) já pronto para ser adicionado ao motor de jogo.
 * 
 * Segue o padrão Factory para separar lógica de criação de objetos do resto do código.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class ObjectCreator {
    /** Instância singleton do motor de jogo */
    private static final GameEngine ge = GameEngine.getInstance();

    /**
     * Cria o GameObject do jogador na posição (x,y) com comportamento e forma padrão.
     * 
     * @param x  coordenada X inicial do jogador
     * @param y  coordenada Y inicial do jogador
     * @return   GameObject configurado como Player
     */
    public static IGameObject Player(double x, double y) {
        GameObject out = new GameObject("Player");
        Transform transform = new Transform(x, y, 0, 0, 1);
        Collider collider = new Collider(new Circle(new Point(400, 300), 20d));
        PlayerBehaviour behaviour = new PlayerBehaviour();
        IShape shape = new TestShape(40);
        out.insertElements(transform, collider, shape, behaviour);
        return out;
    }

    /**
     * Cria um inimigo do tipo Enemy1 na posição (x,y).
     * 
     * @param x  coordenada X inicial do inimigo
     * @param y  coordenada Y inicial do inimigo
     * @return   GameObject configurado como Enemy1
     */
    public static IGameObject Enemy1(double x, double y) {
        GameObject out = new GameObject("Enemy1");
        ITransform transform = new Transform(x, y, 0, 0, 1);
        ICollider collider = new Collider(new Circle(new Point(x, y), 20d));
        ABehaviour behaviour = new EnemyBehaviour1();
        IShape shape = new TestShape(60);
        out.insertElements(transform, collider, shape, behaviour);
        return out;
    }

    /**
     * Cria o GameObject da zona de transição (loading screen) com comportamento STBehaviour.
     * 
     * @param x        coordenada X do portal de transição
     * @param y        coordenada Y do portal de transição
     * @param roomKey  identificador da sala de destino
     * @param posKey   identificador da posição na sala de destino
     * @return         GameObject configurado como loading_screen
     */
    public static IGameObject loading_screen(double x, double y, int roomKey, int posKey) {
        GameObject out = new GameObject("loading_screen");
        ITransform transform = new Transform(x, y, 0, 0, 1);
        ICollider collider = new Collider(new Circle(new Point(x, y), 20d));
        IBehaviour behaviour = new STBehaviour(roomKey, posKey);
        IShape shape = new TestShape(50);
        out.insertElements(transform, collider, shape, behaviour);
        return out;
    }

    /**
     * Cria o chão do nível, ocupando toda a largura da janela,
     * calcula a altura e posiciona o objeto corretamente.
     * 
     * @return GameObject configurado como floor
     */
    public static IGameObject floor() {
        GameObject out = new GameObject("floor");
        int groundHeight = ge.getScreenHeight() / 12;

        // Centro do chão: meio da largura e em baixo, ajustado pela metade da altura
        Transform t2 = new Transform(
            ge.getScreenWidth() / 2,
            ge.getScreenHeight() - groundHeight / 2,
            0, 0, 1
        );

        // Polígono retangular que define a hitbox do chão
        Point[] points = new Point[] {
            new Point(0, ge.getScreenHeight()),
            new Point(ge.getScreenWidth(), ge.getScreenHeight()),
            new Point(ge.getScreenWidth(), ge.getScreenHeight() - groundHeight),
            new Point(0, ge.getScreenHeight() - groundHeight)
        };
        Polygon rect = new Polygon(points);
        Collider c2 = new Collider(rect);
        IBehaviour b2 = new StaticBehaviour();
        IShape shape2 = new BlockShape(ge.getScreenWidth(), groundHeight);
        out.insertElements(t2, c2, shape2, b2);
        return out;
    }

    /**
     * Cria um bloco estático sem collider (se null), apenas com forma e comportamento.
     * 
     * @param x       coordenada X do bloco
     * @param y       coordenada Y do bloco
     * @param width   largura do bloco
     * @param height  altura do bloco
     * @return        GameObject configurado como block
     */
    public static IGameObject block(double x, double y, double width, double height) {
        GameObject block = new GameObject("block");
        ITransform transform = new Transform(x, y, 0, 0, 1);
        IBehaviour behaviour = new BlockBehaviour(x, y, width, height);
        IShape shape = new BlockShape((int)width, (int)height);
        block.insertElements(transform, null, shape, behaviour);
        return block;
    }

    /**
     * Cria uma parede retangular com collider e nome personalizado.
     * 
     * @param x       coordenada X da parede
     * @param y       coordenada Y da parede
     * @param width   largura da parede
     * @param height  altura da parede
     * @param name    nome do GameObject (ex: "leftWall")
     * @return        GameObject configurado como parede
     */
    public static IGameObject blockWall(double x, double y, double width, double height, String name) {
        GameObject wall = new GameObject(name);
        ITransform transform = new Transform(x, y, 0, 0, 1);
        Point[] points = new Point[] {
            new Point(0, 0),
            new Point(0, height),
            new Point(width, height),
            new Point(width, 0)
        };
        ICollider collider = new Collider(new Polygon(points));
        wall.insertElements(transform, collider, null, null);
        return wall;
    }

    /**
     * Cria o objeto de ataque do jogador (hitbox retangular) sem comportamento.
     * Será ativado/desativado pela Entity ou Behaviour de ataque.
     * 
     * @param width   largura do hitbox de ataque
     * @param height  altura do hitbox de ataque
     * @return        GameObject configurado como playerAttack
     */
    public static IGameObject playerAttack1(int width, int height) {
        GameObject attack = new GameObject("playerAttack");
        ITransform transform = new Transform(0, 0, 0, 0, 1);
        Point[] points = new Point[] {
            new Point(0, 0),
            new Point(0, height),
            new Point(width, height),
            new Point(width, 0)
        };
        ICollider collider = new Collider(new Polygon(points));
        IShape shape = new BlockShape(width, height);
        attack.insertElements(transform, collider, shape, null);
        return attack;
    }

    /**
     * Cria o HUD de visualização de vida do jogador.
     * Posiciona-o no canto superior esquerdo e usa uma forma específica.
     * 
     * @return GameObject configurado como healthHUD
     */
    public static IGameObject healthHUD() {
        GameObject healthHUD = new GameObject("healthHUD");
        ITransform transform = new Transform(40, 70, 0, 0, 1);
        IShape shape = new HUDHealthBarShape();
        healthHUD.insertElements(transform, null, shape, null);
        return healthHUD;
    }
}
