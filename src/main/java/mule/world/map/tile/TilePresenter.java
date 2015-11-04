package mule.world.map.tile;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import mule.LandSelectionManager;
import mvp.Presenter;

import javax.inject.Inject;


/**
 * A tileType in the map grid.
 */
public class TilePresenter implements Presenter {
    @Inject private Tile tile;
    @Inject private LandSelectionManager lsMan;

    @FXML private Parent root;
    @FXML private Circle ownerCircle;
    @FXML private Circle playerCircle;
    @FXML private Rectangle muleRect;

    @Override
    public void initialize() {
        // Set the CSS class.
        root.getStyleClass().add(tile.getTileType().getCssClass());

        // Fill in the owner circle with the owner's color.
        tile.getOwnerProp().addListener((obs, oldOwner, owner) -> {
            ownerCircle.setFill(owner.getColor().getColor());
        });
        // Set owner circle initially
        if (tile.getOwner() != null) {
            ownerCircle.setFill(tile.getOwner().getColor().getColor());
        }

        tile.getPlayerProp().addListener((obs, oldPlayer, currPlayer) -> {
            playerCircle.setFill(currPlayer != null ?
                    currPlayer.getColor().getColor() : Color.TRANSPARENT);
        });

        tile.getMuleProp().addListener((obs, oldMule, newMule) -> {
            switch(newMule) {
                case FOOD:
                    muleRect.setFill(Color.rgb(100, 50, 0));
                    break;
                case ENERGY:
                    muleRect.setFill(Color.LIGHTBLUE);
                    break;
                case ORE:
                    muleRect.setFill(Color.GREY);
                    break;
                default:
                    muleRect.setFill(Color.TRANSPARENT);
                    break;
            }
        });
        // Set mule initially.
        if (tile.hasMule()) {
            switch(tile.getMuleProp().get()) {
                case FOOD:
                    muleRect.setFill(Color.rgb(100, 50, 0));
                    break;
                case ENERGY:
                    muleRect.setFill(Color.LIGHTBLUE);
                    break;
                case ORE:
                    muleRect.setFill(Color.GREY);
                    break;
                default:
                    muleRect.setFill(Color.TRANSPARENT);
                    break;
            }
        }
    }

    public void onClick() {
        lsMan.manage(tile);
    }
}

