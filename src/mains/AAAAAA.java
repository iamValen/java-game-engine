package mains;
import engine.*;
import figures.*;

public class AAAAAA {
    public static void main(String[] args) {

        Transform tr1 = new Transform(2, 2, 0, 0, 1);
        Transform tr2 = new Transform(3, 3, 0, 0, 1);

        Figure[] figures = new Figure[2];

        Point[] pts1 = new Point[4];
        pts1[0] = new Point(1, 1);
        pts1[1] = new Point(1, 3);
        pts1[2] = new Point(3, 3);
        pts1[3] = new Point(3, 1);
        figures[0] = new Polygon(pts1);

        Point[] pts2 = new Point[4];
        pts2[0] = new Point(2, 2);
        pts2[1] = new Point(2, 4);
        pts2[2] = new Point(4, 4);
        pts2[3] = new Point(4, 2);
        figures[1] = new Polygon(pts2);



        Collider c1 = new Collider(figures[0]);
        c1.setTransform(tr1);

        Collider c2 = new Collider(figures[1]);
        c2.setTransform(tr2);



        System.out.println(c1.isColliding(c2));
        
        tr2.move(new Point(1, 1), 0);
        c2.onUpdate();

        System.out.println(c1.isColliding(c2));

        tr2.move(new Point(-0.001, -0.001), 0);
        c2.onUpdate();

        System.out.println(c1.isColliding(c2));

        System.out.println();
    }
}
