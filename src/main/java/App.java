import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import configscreen.ConfigScreenView;
import com.airhacks.afterburner.views.FXMLView;

/**
 * This is the main application entry point for the Mule Game.
 *
 * The game is the main project for Georgia Tech's CS 2340 course.
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        final int scale = 20;
//        Parent root = FXMLLoader.load(getClass().getResource("view/ConfigScreen.fxml"));
//        FXMLView startView = new ConfigScreenView();
//
//        Scene scene = new Scene(startView.loadFxml(), 40 * scale, 24 * scale);
//
//        stage.setTitle("M.U.L.E");
//        stage.setScene(scene);
//        stage.show();
        FXMLView startView = new ConfigScreenView();
        Scene scene = new Scene(startView.getView());
        stage.setTitle("M.U.L.E");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }
}
