package mule.mainscreen;

import com.airhacks.afterburner.views.FXMLView;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import mule.world.map.MapView;
import mvp.Presenter;

/**
 * This is the main screen for MULE game.
 *
 * Everything in the game except for the initial configuration
 * screen should be a part of the MainScreen.
 */
public class MainScreenPresenter implements Presenter {
    @FXML private Parent root;
    @FXML private Pane mainContainer;

    @Override
    public void initialize() {
        FXMLView defaultView = new MapView();
        mainContainer.getChildren().add(defaultView.getView());
    }
}
