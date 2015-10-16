import com.airhacks.afterburner.injection.Injector;
import com.airhacks.afterburner.views.FXMLView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mule.configscreen.ConfigScreenView;

/**
 * This is the main application entry point for the Mule Game.
 *
 * The game is the main project for Georgia Tech's CS 2340 course.
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Injector.setModelOrService(stage.getClass(), stage);

        stage.setTitle("M.U.L.E");
        stage.show();

        FXMLView startView = new ConfigScreenView();
        startView.getViewAsync(view -> {
            Scene scene = new Scene(view);
            stage.setScene(scene);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }
}
