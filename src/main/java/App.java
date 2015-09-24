import com.airhacks.afterburner.injection.Injector;
import com.airhacks.afterburner.views.FXMLView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mule.StageProvider;
import mule.configscreen.ConfigScreenView;
import mule.mainscreen.MainScreenView;

import javax.inject.Inject;

/**
 * This is the main application entry point for the Mule Game.
 *
 * The game is the main project for Georgia Tech's CS 2340 course.
 */
public class App extends Application {
    @Inject private StageProvider stageProvider;

    @Override
    public void start(Stage stage) throws Exception {
        Injector.injectMembers(getClass(), this);
        stageProvider.set(stage);

//        FXMLView startView = new ConfigScreenView();
        // TEMPORARY for development purposes.
        // TODO: use env vars to skip config.
        FXMLView startView = new MainScreenView();
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
