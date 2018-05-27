package UI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.Grid;
import logic.Point;

import java.util.List;

public class Painter {

    private GraphicsContext context;
    private int height;
    private int width;
    public static final int PIXEL_SIZE = Grid.PIXEL_SIZE;
    private static final Color BACKGROUND_COLOR = Color.DIMGRAY;
    private static final Color FOOD_COLOR = Color.RED;
    private static final Color SNAKE_COLOR = Color.AZURE;

    public Painter(GraphicsContext context, int height, int width) {
        this.context = context;
        this.height = height;
        this.width = width;
    }

    public void fillBackground() {
        context.setFill(BACKGROUND_COLOR);
        for (int x = 0; x < width; x += PIXEL_SIZE) {
            for (int y = 0; y < height; y += PIXEL_SIZE) {
                context.fillRect(x, y, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    public void fillFood(Point food) {
        addPixel(food, FOOD_COLOR);
    }

    public void addPixel(Point point, Color color) {
        context.setFill(color);
        context.fillRect(point.getX(), point.getY(), PIXEL_SIZE, PIXEL_SIZE);
    }

    public void fillMoveSnake(List<Point> snake, Point oldPosition) {
        addPixel(oldPosition, BACKGROUND_COLOR);
        fillSnake(snake);
    }

    public void fillSnake(List<Point> points) {
        points.forEach(point -> addPixel(point, SNAKE_COLOR));
    }
}