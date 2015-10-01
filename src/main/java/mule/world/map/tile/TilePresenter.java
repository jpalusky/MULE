package mule.world.map.tile;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import mule.GameState;
import mule.LandSelectionManager;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;


/**
 * A tileType in the map grid.
 */
public class TilePresenter implements Presenter {
    @Inject private GameState gameState;
    @Inject private Tile tile;
    @Inject private LandSelectionManager lsMan;

    @FXML private Parent root;
    @FXML private Circle ownerCircle;
    @FXML private Circle playerCircle;

    @Override
    public void initialize() {
        // Set the CSS class.
        root.getStyleClass().add(tile.getTileType().getCssClass());

        // Fill in the owner circle with the owner's color.
        tile.getOwnerProp().addListener((obs, oldOwner, owner) -> {
            ownerCircle.setFill(owner.getColor().getColor());
        });

        tile.getPlayerProp().addListener((obs, oldPlayer, currPlayer) -> {
            playerCircle.setFill(currPlayer != null ?
                    currPlayer.getColor().getColor() : Color.TRANSPARENT);
        });
    }

    public void onClick() {
        lsMan.manage(tile);
    }
}

