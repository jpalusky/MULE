import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    Rectangle rect;
    GraphicsContext gc;
    final int WINDOW_HEIGHT = 480;
    final int WINDOW_WIDTH = 640;
    Screen currScreen;

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        currScreen = new ConfigScreen(this, scene);
        new AnimationTimer() {
            public void handle(long gt) {
                update();
                render(gc);
            }
        }.start();
        primaryStage.setTitle("M.U.L.E");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void render(GraphicsContext gc) {
        //Clear background
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        gc.setFill(Color.BLACK);
        //Draw screen
        currScreen.draw(gc, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void update() {

    }

    public void switchScreen(Screen screen) {
        currScreen = screen;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
