package mule.mainscreen;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.layout.Pane;

public class MainScreen extends Pane {
    private Pane root;

    // Empty constructor for injector.
    public MainScreen() {
    }

    public MainScreen(Pane root) {
        this.root = root;
    }

    public void setView(FXMLView view) {
        root.getChildren().clear();
        view.getViewAsync(v -> root.getChildren().add(v));
    }
}
