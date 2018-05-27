package logic;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<Point> points;
    private static int size = 5;

    public Snake(Point head) {
        points = new ArrayList<>();
        points.add(head);
        for (int shift = 1; shift < size; shift++) {
            points.add(new Point(head.getX() + shift * Grid.PIXEL_SIZE, head.getY()));
        }
    }

    public void move(Point point) {
        points.remove(points.size() - 1);
        points.add(0, point);
    }

    public void addSize(Point point) {
        points.add(point);
    }

    public static void setSize(int newSize) {
        size = newSize;
    }

    public List<Point> getPoints() {
        return points;
    }
}
