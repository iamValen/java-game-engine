package gui;

import behaviour.*;
import engine.Collider;
import engine.GameObject;
import engine.TestShape;
import engine.Transform;
import figures.Circle;
import figures.Point;
import interfaces.*;

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

}
