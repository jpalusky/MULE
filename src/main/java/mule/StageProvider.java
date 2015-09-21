package mule;

import javafx.stage.Stage;

import javax.inject.Provider;

/**
 * Provider for the JavaFX primaryStage.
 */
public class StageProvider implements Provider<Stage> {
    private Stage stage;

    @Override
    public Stage get() {
        return stage;
    }

    public void set(Stage stage) {
        this.stage = stage;
    }
}
