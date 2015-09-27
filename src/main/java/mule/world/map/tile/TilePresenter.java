package mule.world.map.tile;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import mule.GameState;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;


/**
 * A tileType in the map grid.
 */
public class TilePresenter implements Presenter {
    @Inject private GameState gameState;
    @Inject private Tile tile;

    @FXML private Parent root;
    @FXML private Circle ownerCircle;

    @Override
    public void initialize() {
        root.getStyleClass().add(tile.getTileType().getCssClass());

        tile.getOwnerProp().addListener((obs, oldOwner, owner) -> {
            ownerCircle.setFill(owner.getColor().getColor());
        });
    }

    public void onClick() {
        System.out.println(gameState);
        handleLandSelection();
    }

    private void handleLandSelection() {
        if (!gameState.getInSelectionPhase()) return;

        if (gameState.getCurrentPlayer().buyProperty(tile))
            gameState.incCounter();
    }
}

