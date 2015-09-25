package mule.world.map.tile;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import mule.player.Player;
import mvp.Presenter;


/**
 * A tileType in the map grid.
 */
public class TilePresenter implements Presenter {
    @FXML private Pane root;
    @FXML private Circle ownerCircle;
    @FXML private Circle playerCircle;

    private TileType tileType;
    private Player owner;

    @Override
    public void initialize() {
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    /**
     * Sets the owner of the tile. If there is already an owner, return false.
     *
     * @param player the owner to set.
     * @return whether or not the owner has changed.
     */
    public boolean setOwner(Player player) {
        if (owner != null) return false;
        owner = player;
        ownerCircle.setFill(player.getColor().getColor());
        return true;
    }

    public Pane getRoot() {
        return root;
    }

    public void addPlayer(Player player) {
        playerCircle.setFill(player.getColor().getColor());
    }

    public void removePlayer() {
        playerCircle.setFill(Color.TRANSPARENT);
    }
}

