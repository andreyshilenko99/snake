package logic;

import UI.ScreenCreator;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

public class GameLoop implements Runnable {
    private final Grid grid;
    private float interval;
    private static boolean running;
    private static int frameRate = 20;
    private Canvas canvas;
    private KeyCode currentKey = KeyCode.LEFT;

    public GameLoop(Grid grid, Canvas canvas) {
        this.grid = grid;
        interval = 1000.0f / frameRate;
        running = true;
        this.canvas = canvas;
        this.canvas.setFocusTraversable(true);
    }

    @Override
    public void run() {
        while (running) {
            canvas.setOnKeyPressed(e -> currentKey = e.getCode());
            switch (currentKey) {
                case UP:
                    if (grid.getCurrentMove() != Grid.Move.DOWN) {
                        grid.snakeMove(Grid.Move.UP);
                    } else {
                        currentKey = KeyCode.DOWN;
                        grid.snakeMove(Grid.Move.DOWN);
                    }
                    break;
                case RIGHT:
                    if (grid.getCurrentMove() != Grid.Move.LEFT) {
                        grid.snakeMove(Grid.Move.RIGHT);
                    } else {
                        currentKey = KeyCode.LEFT;
                        grid.snakeMove(Grid.Move.LEFT);
                    }
                    break;
                case DOWN:
                    if (grid.getCurrentMove() != Grid.Move.UP) {
                        grid.snakeMove(Grid.Move.DOWN);
                    } else {
                        currentKey = KeyCode.UP;
                        grid.snakeMove(Grid.Move.UP);
                    }
                    break;
                case LEFT:
                    if (grid.getCurrentMove() != Grid.Move.RIGHT) {
                        grid.snakeMove(Grid.Move.LEFT);
                    } else {
                        currentKey = KeyCode.RIGHT;
                        grid.snakeMove(Grid.Move.RIGHT);
                    }
                    break;
            }

            float time = System.currentTimeMillis();
            time = System.currentTimeMillis() - time;
            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException ignore) {
                }
            }
        }
    }

    public static void setFrameRate(int newFrameRate) {
        frameRate = newFrameRate;
    }

    public void gameOver() {
        running = false;
        Platform.runLater(ScreenCreator::gameOver);
    }
}