package logic;
import java.util.Objects;
import java.util.Random;

public class Point {
    private int x;
    private int y;

    Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Point shift(int dx, int dy) {
        return new Point(x + dx, y + dy);
    }

    public static Point getRandomPoint(int maxX, int maxY) {
        int randomX;
        int randomY;
        do {
            randomX = new Random().nextInt(maxX - Grid.PIXEL_SIZE);
            randomY = new Random().nextInt(maxY - Grid.PIXEL_SIZE);
        } while (randomX % Grid.PIXEL_SIZE != 0 || randomY % Grid.PIXEL_SIZE != 0);
        return new Point(randomX, randomY);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "X = " + x + " Y = " + y;
    }
}
