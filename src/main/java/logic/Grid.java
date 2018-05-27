package logic;

import UI.Painter;
import javafx.scene.canvas.GraphicsContext;

public class Grid {

    public static final int PIXEL_SIZE = 10;
    private int height;
    private int width;
    private Snake snake;
    private Point food = null;
    private GameLoop loop;
    private Painter painter;
    private Move currentMove = Move.LEFT;

    public void setLoop(GameLoop loop) {
        this.loop = loop;
    }

    public Move getCurrentMove() {
        return currentMove;
    }

    public enum Move {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public Grid(GraphicsContext context, double height, double width) {
        this.height = (int) height;
        this.width = (int) width;
        painter = new Painter(context, (int) height, (int) width);
        snake = new Snake(Point.getRandomPoint((int) width, (int) height));
        painter.fillBackground();
        painter.fillSnake(snake.getPoints());
    }

    public void snakeMove(Move move) {
        Point head = snake.getPoints().get(0);
        Point tail = snake.getPoints().get(snake.getPoints().size() - 1);
        Point newPosition;
        if (food == null) {
            createFood();
            painter.fillFood(food);
        }
        currentMove = move;
        switch (move) {
            case UP:
                newPosition = head.shift(0, -PIXEL_SIZE);
                break;
            case RIGHT:
                newPosition = head.shift(PIXEL_SIZE, 0);
                break;
            case DOWN:
                newPosition = head.shift(0, PIXEL_SIZE);
                break;
            default:
                newPosition = head.shift(-PIXEL_SIZE, 0);
                break;
        }

        if (newPosition.getX() < 0) {
            newPosition.setX(width - PIXEL_SIZE);
        } else if (newPosition.getX() > width - PIXEL_SIZE) {
            newPosition.setX(0);
        } else if (newPosition.getY() < 0) {
            newPosition.setY(height - PIXEL_SIZE);
        } else if (newPosition.getY() > height - PIXEL_SIZE) {
            newPosition.setY(0);
        }

        if (snake.getPoints().contains(newPosition)) {
            loop.gameOver();
            return;
        }
        snake.move(newPosition);
        if (newPosition.equals(food)) {
            snake.addSize(food);
            createFood();
            painter.fillFood(food);
        }
        painter.fillMoveSnake(snake.getPoints(), tail);
    }

    private void createFood() {
        food = Point.getRandomPoint(width, height);
    }
}