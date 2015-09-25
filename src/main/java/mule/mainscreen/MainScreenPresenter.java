package mule.mainscreen;

import com.airhacks.afterburner.views.FXMLView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import mule.GameState;
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
    @Inject private MainContainerProvider mainContainerProvider;
    @Inject private GameState gameState;

    @FXML private Pane mainContainer;

    @Override
    public void initialize() {
        mainContainerProvider.set(mainContainer);
        FXMLView defaultView = new MapView();
        mainContainer.getChildren().add(0, defaultView.getView());

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameState.getInSelectionPhase()) return;
                // not sure if I need a game loop...
            }
        };
        gameLoop.start();
    }
}
