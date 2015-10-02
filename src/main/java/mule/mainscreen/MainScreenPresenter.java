package mule.mainscreen;

import com.airhacks.afterburner.injection.Injector;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import mule.LandSelectionManager;
import mule.TurnManager;
import mule.world.map.MapView;
import mvp.Presenter;

import javax.inject.Inject;

/**
 * This is the main screen for MULE game.
 *
 * Everything in the game except for the initial configuration
 * screen should be a part of the MainScreen.
 */
public class MainScreenPresenter implements Presenter {
    @Inject private LandSelectionManager landSelectionManager;
    @Inject private TurnManager turnManager;

    @FXML private Pane mainContainer;

    @Override
    public void initialize() {
        MainScreen mainScreen = new MainScreen(mainContainer);
        Injector.setModelOrService(mainScreen.getClass(), mainScreen);

        mainScreen.setView(new MapView());

        landSelectionManager.getInLandSelectionPhaseProp().addListener((obs, old, newValue) -> {
            if (!newValue) turnManager.start();
        });
    }
}
