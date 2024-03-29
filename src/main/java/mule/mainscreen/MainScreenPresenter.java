package mule.mainscreen;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import mule.KeyHandler;
import mule.LandSelectionManager;
import mule.TurnManager;
import mule.world.map.MapView;
import mule.world.town.TownView;
import mvp.Presenter;

import javax.inject.Inject;

/**
 * This is the main screen for MULE game.
 *
 * Everything in the game except for the initial configuration
 * screen should be a part of the MainScreen.
 */
public class MainScreenPresenter implements Presenter {
    @Inject private MainScreen mainScreen;
    @Inject private LandSelectionManager landSelectionManager;
    @Inject private TurnManager turnManager;
    @Inject private KeyHandler keyHandler;

    @FXML private Pane mainContainer;

    @Override
    public void initialize() {
        mainScreen.configure(mainContainer, new MapView(), new TownView());

        mainScreen.showMap();

        landSelectionManager.getInLandSelectionPhaseProp().addListener((obs, old, newValue) -> {
            if (!newValue) turnManager.start();
        });
    }
}
