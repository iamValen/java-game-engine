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
import shapes.TestShape;

/**
 * os objetos vao ser todos criados aqui
 */
public class ObjectCreator {
    private static final GameEngine ge = GameEngine.getInstance();

    public static IGameObject Player(double x, double y) {
        GameObject out = new GameObject("Player");
        Transform transform = new Transform(x, y, 0, 0, 1);
        Collider collider = new Collider(new Circle(new Point(400, 300), 20d));
        PlayerBehaviour behaviour = new PlayerBehaviour();
        IShape shape = new TestShape(40);
        out.insertElements(transform, collider, shape, behaviour);

        return out;
    }



    public static IGameObject Enemy1(double x, double y){

        GameObject out = new GameObject("Enemy1");
        ITransform transform = new Transform(x, y, 0, 0, 1);
        ICollider collider = new Collider(new Circle(new Point(x, y), 20d));
        ABehaviour behaviour = new EnemyBehaviour1();
        IShape shape = new TestShape(60);
        out.insertElements(transform, collider, shape, behaviour);

        return  out;
    }




    public static IGameObject loading_screen(double x, double y, int roomKey, int posKey) {
        GameObject out = new GameObject("loading_screen");
        ITransform transform = new Transform(x, y, 0, 0, 1);
        ICollider collider = new Collider(new Circle(new Point(x, y), 20d));
        IBehaviour behaviour = new STBehaviour(roomKey, posKey);
        IShape shape = new TestShape(50);
        out.insertElements(transform, collider, shape, behaviour);

        return out;
    }

    public static IGameObject floor() {

        GameObject out = new GameObject("floor");

        int groundHeight = ge.getScreenHeight() / 12;

        Transform t2 = new Transform(ge.getScreenWidth() / 2, (ge.getScreenHeight() - groundHeight / 2), 0, 0, 1);
        Point p1 = new Point(0, ge.getScreenHeight());
        Point p2 = new Point(ge.getScreenWidth(), ge.getScreenHeight());
        Point p3 = new Point(ge.getScreenWidth(), ge.getScreenHeight() - groundHeight);
        Point p4 = new Point(0, ge.getScreenHeight() - groundHeight);
        Point[] points = {p1, p2, p3, p4};
        Polygon rect = new Polygon(points);
        Collider c2 = new Collider(rect);
        StaticBehaviour b2 = new StaticBehaviour();
        IShape shape2 = new BlockShape(ge.getScreenWidth(), groundHeight);
        out.insertElements(t2, c2, shape2, b2);

        return out;
    }

    public static IGameObject block(double x, double y, double width, double height){
        GameObject block = new GameObject("block");
        ITransform transform = new Transform(x, y, 0, 0, 1);
        IBehaviour behaviour = new BlockBehaviour(x, y, width, height);
        IShape shape = new BlockShape((int)width, (int)height);
        block.insertElements(transform, null, shape, behaviour);
        return block;
    }

    public static IGameObject blockWall(double x, double y, double width, double  height, String name){
        GameObject wall = new GameObject(name);
        ITransform transform = new Transform(x, y, 0, 0, 1);
        Point[] points = new Point[4];
        points[0] = new Point(0, 0);
        points[1] = new Point(0, height);
        points[2] = new Point(width, height);
        points[3] = new Point(width, 0);
        ICollider collider = new Collider(new Polygon(points));
        wall.insertElements(transform, collider, null, null);
        return wall;
    }
}
