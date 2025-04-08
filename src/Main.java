import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int frames = Integer.parseInt(scanner.nextLine());
        int n = Integer.parseInt(scanner.nextLine());

        GameEngine engine = new GameEngine();

        for (int i = 0; i < n; i++) {
            String name = scanner.nextLine();
            String[] transformData = scanner.nextLine().split(" ");
            double x = Double.parseDouble(transformData[0]);
            double y = Double.parseDouble(transformData[1]);
            int layer = Integer.parseInt(transformData[2]);
            double angle = Double.parseDouble(transformData[3]);
            double scale = Double.parseDouble(transformData[4]);

            String[] colliderData = scanner.nextLine().split(" ");
            Figura figura;
            if (colliderData.length == 3) {
                double cx = Double.parseDouble(colliderData[0]);
                double cy = Double.parseDouble(colliderData[1]);
                double radius = Double.parseDouble(colliderData[2]);
                figura = new Circulo(new Point(cx, cy), radius);
            } else {
                Point[] points = new Point[colliderData.length / 2];
                for (int j = 0; j < colliderData.length; j += 2) {
                    double px = Double.parseDouble(colliderData[j]);
                    double py = Double.parseDouble(colliderData[j + 1]);
                    points[j / 2] = new Point(px, py);
                }
                figura = new Poligono(points);
            }

            String[] velocityData = scanner.nextLine().split(" ");
            double vx = Double.parseDouble(velocityData[0]);
            double vy = Double.parseDouble(velocityData[1]);
            int vLayer = Integer.parseInt(velocityData[2]);
            double vAngle = Double.parseDouble(velocityData[3]);
            double vScale = Double.parseDouble(velocityData[4]);

            GameObject go = new GameObject(name, x, y, layer, angle, scale, figura);
            go.posSpeed = new Point(vx, vy);
            go.layerSpeed = vLayer;
            go.rotationSpeed = vAngle;
            go.scaleSpeed = vScale;

            engine.add(go);
        }

        engine.simulateFrames(frames);
        List<String> collisions = engine.detectCollisions();

        for (String collision : collisions) {
            System.out.println(collision);
        }

        scanner.close();
    }
}
