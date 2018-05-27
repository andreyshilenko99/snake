package UI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.GameLoop;
import logic.Grid;
import logic.Snake;

import java.util.Optional;

public class ScreenCreator extends Application {

    private static Canvas canvas;
    private static GraphicsContext context;
    private static Stage fieldStage = null;
    private static int snakeSize = 3;
    private static int snakeSpeed = 10;

    public void start(Stage primaryStage) {
        createStartMenu();
    }

    private static void createStartMenu() {
        Stage menuStage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 400, 300);
        scene.getStylesheets().add(ScreenCreator.class.getResource("/style/style.css").toExternalForm());

        menuStage.setScene(scene);

        int[] sizes = new int[]{3, 5, 10, 30, 200};
        ChoiceBox<String> choiceSize = new ChoiceBox<>(FXCollections.observableArrayList(
                "For little bitchli's", "Normal", "Middle", "Long", "WTF SO LONG"));
        choiceSize.getSelectionModel().selectFirst();
        int[] speeds = new int[]{10, 20, 25, 40, 50};
        ChoiceBox<String> choiceSpeed = new ChoiceBox<>(FXCollections.observableArrayList(
                "For little bitchli's", "Normal", "Middle", "Hard", "WTF SO SPEED"));
        choiceSpeed.getSelectionModel().selectFirst();

        choiceSize.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
                snakeSize = sizes[newValue.intValue()]);
        choiceSpeed.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
                snakeSpeed = speeds[newValue.intValue()]);

        Label sizeLabel = new Label("Size");
        sizeLabel.setAlignment(Pos.CENTER);
        Label speedLabel = new Label("Speed");
        speedLabel.setAlignment(Pos.CENTER);

        Button startGameButton = new Button("Start game!");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(startGameButton);
        buttonBox.setAlignment(Pos.CENTER);

        startGameButton.setOnMouseClicked(event -> {
            createFirstStartField(snakeSize, snakeSpeed);
            menuStage.close();
        });

        grid.add(sizeLabel, 0, 0);
        grid.add(speedLabel, 1, 0);
        grid.add(choiceSize, 0, 1);
        grid.add(choiceSpeed, 1, 1);
        grid.add(buttonBox, 0, 2, 2, 1);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        menuStage.setResizable(false);
        menuStage.setTitle("Snake");
        menuStage.show();
    }

    private static void createFirstStartField(int startSnakeSize, int snakeSpeed) {
        if (fieldStage != null) fieldStage.close();
        fieldStage = new Stage();

        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);

        canvas = new Canvas(600, 600);
        context = canvas.getGraphicsContext2D();

        createNewGame(startSnakeSize, snakeSpeed);

        root.getChildren().add(canvas);
        fieldStage.setScene(scene);
        fieldStage.setResizable(false);
        fieldStage.setTitle("Snake");
        fieldStage.setOnCloseRequest(e -> System.exit(0));
        fieldStage.show();
    }

    private static void createNewGame(int startSnakeSize, int snakeSpeed) {

        GameLoop.setFrameRate(snakeSpeed);
        Snake.setSize(startSnakeSize);

        Grid grid = new Grid(context, canvas.getHeight(), canvas.getWidth());

        GameLoop loop = new GameLoop(grid, canvas);
        (new Thread(loop)).start();

        grid.setLoop(loop);
    }

    public static void gameOver() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("U lose");
        alert.setContentText("Again?");
        Optional<ButtonType> alertButton = alert.showAndWait();

        if (alertButton.get() == ButtonType.OK) {
            createStartMenu();
        } else {
            System.exit(0);
        }
    }

}