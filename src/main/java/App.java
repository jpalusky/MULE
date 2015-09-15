import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main application entry point for the Mule Game.
 *
 * The game is the main project for Georgia Tech's CS 2340 course.
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final int scale = 15;
        Parent root = FXMLLoader.load(getClass().getResource("view/ConfigScreen.fxml"));
        Scene scene = new Scene(root, 40 * scale, 24 * scale);

        stage.setTitle("M.U.L.E");
        stage.setScene(scene);
        stage.show();
    }
}
