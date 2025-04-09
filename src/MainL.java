import java.util.Scanner;

/**
 * Main - L
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class MainL {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine().trim();

        String[] transformTokens = sc.nextLine().trim().split("\\s+");
        double x = Double.parseDouble(transformTokens[0]);
        double y = Double.parseDouble(transformTokens[1]);
        int layer = Integer.parseInt(transformTokens[2]);
        double angle = Double.parseDouble(transformTokens[3]);
        double scale = Double.parseDouble(transformTokens[4]);

        String[] colliderTokens = sc.nextLine().trim().split("\\s+");
        sc.close();
        Figure fig;
        if(colliderTokens.length == 3) {
            double cx = Double.parseDouble(colliderTokens[0]);
            double cy = Double.parseDouble(colliderTokens[1]);
            double r = Double.parseDouble(colliderTokens[2]);
            fig = new Circle(new Point(cx, cy), r);
        }
        else {
            int nVertices = colliderTokens.length / 2;
            Point[] points = new Point[nVertices];
            for (int i = 0; i < nVertices; i++) {
                double vx = Double.parseDouble(colliderTokens[2*i]);
                double vy = Double.parseDouble(colliderTokens[2*i+1]);
                points[i] = new Point(vx, vy);
            }
            fig = new Polygon(points);
        }

        GameObject gameObj = new GameObject(name, x, y, layer, angle, scale, fig);

        System.out.println(gameObj.name());
        System.out.println(gameObj.transform().toString());
        System.out.println(gameObj.collider().toString());

    }
}
