package gui;

import behaviour.EnemyBehaviour1;
import behaviour.PlayerBehaviour;
import engine.Collider;
import engine.GameEngine;
import engine.GameObject;
import engine.TestShape;
import engine.Transform;
import figures.Circle;
import figures.Point;
import interfaces.IShape;

public class Loader {
    public void loadLevel() {
        GameEngine engine = GameEngine.getInstance();

        GameObject test = new GameObject("Test");
        Transform t1 = new Transform(400, 300, 0, 0, 1);
        Collider c1 = new Collider(new Circle(new Point(400, 300), 20d));
        PlayerBehaviour b1 = new PlayerBehaviour(test);
        IShape shape = new TestShape(40);
        test.insertElements(t1, c1, shape, b1);

        engine.addEnabled(test);


        GameObject test1 = new GameObject("Test");
        Transform t2 = new Transform(100, 300, 0, 0, 1);
        Collider c2 = new Collider(new Circle(new Point(100, 300), 20d));
        EnemyBehaviour1 b2 = new EnemyBehaviour1(test1);
        IShape shape2 = new TestShape(60);
        test1.insertElements(t2, c2, shape2, b2);

        engine.addEnabled(test1);

        System.out.println("Player loaded");
    }
}