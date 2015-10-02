package mule.mainscreen;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.layout.Pane;

public class MainScreen extends Pane {
    private Pane root;

    private FXMLView map;
    private FXMLView town;

    public void configure(Pane root, FXMLView map, FXMLView town) {
        this.root = root;
        this.map = map;
        this.town = town;
    }

    public void showTown() {
        root.getChildren().clear();
        town.getViewAsync(v -> root.getChildren().add(v));
    }

    public void showMap() {
        root.getChildren().clear();
        map.getViewAsync(v -> root.getChildren().add(v));
    }
}
