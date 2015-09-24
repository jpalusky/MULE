package mule.world.map.tile;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import mvp.Presenter;


/**
 * A tileType in the map grid.
 */
public class TilePresenter implements Presenter {
    @FXML private Pane root;
    @FXML private Circle ownerCircle;

    private TileType tileType;

    @Override
    public void initialize() {
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }
}
