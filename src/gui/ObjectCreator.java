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
        GameEngine ge = GameEngine.getInstance();

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

    public static IGameObject block(double x, double y) {
        return null;
    }

}
