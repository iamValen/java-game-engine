package gui;

import behaviour.*;
import engine.Collider;
import engine.GameEngine;
import engine.GameObject;
import engine.Transform;
import figures.Point;
import figures.Polygon;
import interfaces.*;
import java.awt.Color;
import shapes.BlockShape;
import shapes.HealthShape;
import shapes.ScoreShape;

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

    private static Collider squareHitbox(int width, int height){
        Point[] pts = new Point[4];
        pts[0] = new Point(0, 0);
        pts[1] = new Point(0, height);
        pts[2] = new Point(width, height);
        pts[3] = new Point(width, 0);
        return new Collider(new Polygon(pts));
    }

    /**
     * Cria o GameObject do jogador na posição (x,y) com comportamento e forma padrão.
     * 
     * @param x  coordenada X inicial do jogador
     * @param y  coordenada Y inicial do jogador
     * @return   GameObject configurado como Player
     */
    public static IGameObject Player(double x, double y, int layer, double rotation, double scale,
    int width, int height){
        GameObject out = new GameObject("Player");
        Transform transform = new Transform(x, y, layer, rotation, scale);
        Collider collider = squareHitbox(width, height);
        PlayerBehaviour behaviour = new PlayerBehaviour(width, height);
        IShape shape = new BlockShape(width, height, Color.BLUE);
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
    public static IGameObject Enemy1(double x, double y, int layer, double rotation, double scale,
    int width, int height){
        GameObject out = new GameObject("Enemy1");
        ITransform transform = new Transform(x, y, layer, rotation, scale);
        ICollider collider = squareHitbox(width, height);
        ABehaviour behaviour = new EnemyBehaviour1(50, width, height);
        IShape shape = new BlockShape(width, height, Color.BLUE);
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
    public static IGameObject loading_screen(double x, double y, int layer, double rotation, double scale,
    int width, int height, int roomKey, int posKey){
        GameObject out = new GameObject("loading_screen");
        ITransform transform = new Transform(x, y, layer, rotation, scale);
        ICollider collider = squareHitbox(width, height);
        IBehaviour behaviour = new STBehaviour(roomKey, posKey);
        IShape shape = new BlockShape(50, 50, Color.BLACK);
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
        IShape shape2 = new BlockShape(ge.getScreenWidth(), groundHeight, Color.GREEN);
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
    public static IGameObject block(double x, double y, int layer, double rotation, double scale,
    int width, int height){
        GameObject block = new GameObject("block");
        ITransform transform = new Transform(x, y, layer, rotation, scale);
        IBehaviour behaviour = new BlockBehaviour(x, y, width, height);
        IShape shape = new BlockShape(width, height, Color.GREEN);
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
    public static IGameObject blockWall(double x, double y, int layer, double rotation, double scale,
    int width, int height, String name){
        GameObject wall = new GameObject(name);
        ITransform transform = new Transform(x, y, layer, rotation, scale);
        Collider collider = squareHitbox(width, height);
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
        ICollider collider = squareHitbox(width, height);
        IShape shape = new BlockShape(width, height, Color.GREEN);
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
        ITransform transform = new Transform(40, 70, Integer.MAX_VALUE, 0, 1);
        IShape shape = new HealthShape();
        healthHUD.insertElements(transform, null, shape, null);
        return healthHUD;
    }

    public static IGameObject meleeAtack(double x, double y, int layer, double rotation, double scale,
    int damage, int width, int height, long duration, Physics physics, String name){
        GameObject attack = new GameObject(name);
        ITransform transform = new Transform(x, y, layer, rotation, scale);
        ICollider collider = squareHitbox(width, height);
        IBehaviour behaviour = new meleeAttackBehaviour(damage, duration, physics);
        IShape shape = new BlockShape(width, height, Color.GREEN);
        attack.insertElements(transform, collider, shape, behaviour);
        return attack;
    }

    public static IGameObject score(){
        IGameObject score = new GameObject("score");
        ITransform transform = new Transform(1400, 50, 0, 0, 1);
        IShape shape = new ScoreShape();
        score.insertElements(transform, null, shape, null);
        return score;
    }
}
