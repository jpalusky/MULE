package mule.world.map;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import mule.world.map.tile.TilePresenter;
import mule.world.map.tile.TileType;
import mule.world.map.tile.TileView;
import mvp.Presenter;

/**
 * MapPresenter is the presenter for the Map.
 *
 * The map coordinates start at (0, 0) at the top left corner.
 */
public class MapPresenter implements Presenter {
    @FXML private GridPane grid;

    private TilePresenter[][] tiles;

    // Some aliases to make it easier to write the standard map.
    private TileType R = TileType.RIVER;
    private TileType P = TileType.PLAIN;
    private TileType M1 = TileType.MOUNTAIN1;
    private TileType M2 = TileType.MOUNTAIN2;
    private TileType M3 = TileType.MOUNTAIN3;

    /** The standard map. */
    private final TileType[][] standardMap;

    public MapPresenter() {
        standardMap = new TileType[][] {
                {P, P, M1, P, R, P, M3, P, P},
                {P, M1, P, P, R, P, P, P, M3},
                {M3, P, P, P, TileType.TOWN, P, P, P, M1},
                {P, M2, P, P, R, P, M2, P, P},
                {P, P, M2, P, R, P, P, P, M2}
        };
    }

    @Override
    public void initialize() {
        tiles = new TilePresenter[5][9];
        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 5; ++y) {
                // TODO: Allow map switching for randomly generated maps.
                TileType[][] map = standardMap;

                TileView tile = new TileView(map[y][x]);
                grid.add(tile.getView(), x, y);
                tiles[y][x] = tile.getPresenter();
            }
        }
    }
}
