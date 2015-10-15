package mule.mainscreen;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.layout.Pane;
import mule.world.map.MapPresenter;
import mule.world.town.TownPresenter;

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
        ((MapPresenter) map.getPresenter()).unbindInput();
        ((TownPresenter) town.getPresenter()).bindInput();
        ((TownPresenter) town.getPresenter()).resetPlayer();
    }

    public void showMap() {
        root.getChildren().clear();
        map.getViewAsync(v -> root.getChildren().add(v));
        ((TownPresenter) town.getPresenter()).unbindInput();
        ((MapPresenter) map.getPresenter()).bindInput();
    }
}
