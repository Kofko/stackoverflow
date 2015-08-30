import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * http://stackoverflow.com/questions/30951045/square-with-minimum-area-enclosing-k-points-among-given-n-points
 * <p/>
 * There are N points given in a Cartesian plane. An integer K will be given. The aim is to find the area of the square (minimum) enclosing at least K points.
 */
public class MinSquareKPoints {

    public static void main(String[] args) {
        int N = 20;
        int K = 4;
        int minArea = Integer.MAX_VALUE;

        // generate points
        List<Point2D> points = new ArrayList<Point2D>();
        Random rnd = new Random(1);
        for (int i = 0; i < N; ++i) {
            points.add(new Point2D.Double(rnd.nextInt(100), rnd.nextInt(100)));
        }

        // sort points by X
        List<Point2D> sortedX = new ArrayList<Point2D>(points);
        Collections.sort(sortedX, new Comparator<Point2D>() {
            @Override
            public int compare(Point2D o1, Point2D o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });

        // sort points by Y
        List<Point2D> sortedY = new ArrayList<Point2D>(points);
        Collections.sort(sortedY, new Comparator<Point2D>() {
            @Override
            public int compare(Point2D o1, Point2D o2) {
                return Double.compare(o1.getY(), o2.getY());
            }
        });

        // iterate through all combinations of left and bottom edges
        Point2D corner = null;
        for (int i = 0; i < N - K + 1; ++i) {
            for (int j = 0; j < N - K + 1; ++j) {
                Point2D leftPoint = sortedX.get(i);
                Point2D bottomPoint = sortedY.get(j);
                if (leftPoint.getX() > bottomPoint.getX() || leftPoint.getY() < bottomPoint.getY()) {
                    continue;
                }
                int leftEdge = (int) leftPoint.getX() - 1;
                int bottomEdge = (int) bottomPoint.getY() - 1;
                // get points that are above and to the right of the corner
                Set<Point2D> acceptablePoints = new HashSet<Point2D>(sortedX.subList(i, N));
                acceptablePoints.retainAll(new HashSet<Point2D>(sortedY.subList(j, N)));
                if (acceptablePoints.size() < K) {
                    continue; // not enough points
                }
                // calculate and sort areas
                List<Integer> areas = new ArrayList<Integer>();
                for (Point2D point : acceptablePoints) {
                    int squareSide = (int) Math.max(point.getX() + 1 - leftEdge, point.getY() + 1 - bottomEdge);
                    areas.add(squareSide * squareSide);
                }
                Collections.sort(areas);
                // update minArea
                int currentArea = areas.get(K - 1);
                if (minArea > currentArea) {
                    minArea = currentArea;
                    corner = new Point2D.Float(leftEdge, bottomEdge); // for debug only
                }
            }
        }
        // display output
        System.out.println("Min area: " + minArea);
        drawSquare(points, corner, minArea);
    }

    public static void drawSquare(final List<Point2D> points, final Point2D sqCorner, final int minArea) {
        JFrame jFrame = new JFrame("Squares");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final int s = 6;
        jFrame.setSize(100 * s, 100 * s);
        jFrame.setResizable(false);
        jFrame.add(new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.GRAY);
                int side = (int) Math.sqrt(minArea);
                g.fillRect((int) sqCorner.getX() * s - 2, (int) sqCorner.getY() * s - 2, side * s + 4, side * s + 4);
                g.setColor(Color.BLACK);
                for (Point2D point : points) {
                    g.fillOval((int) point.getX() * s - 2, (int) point.getY() * s - 2, 4, 4);
                }
            }
        });
        jFrame.setVisible(true);
    }
}
