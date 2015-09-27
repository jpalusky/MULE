package mule.world.map;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import mule.GameState;
import mule.KeyHandler;
import mule.mainscreen.MainContainerProvider;
import mule.world.map.tile.Tile;
import mule.world.map.tile.TileType;
import mule.world.map.tile.TileView;
import mvp.Presenter;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * MapPresenter is the presenter for the Map.
 *
 * The map coordinates start at (0, 0) at the top left corner.
 */
public class MapPresenter implements Presenter {
    @Inject private GameState gameState;
    @Inject private KeyHandler keyHandler;
    @Inject private MainContainerProvider mainContainerProvider;
    @Inject private Map map;

    @FXML private GridPane grid;

    @Override
    public void initialize() {
        // TODO: Allow map switching for randomly generated maps.
        TileType[][] mapType = Map.getStandardMap();
        map.initialize(mapType);

        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 5; ++y) {
                Tile tile = map.getTile(x, y);
                HashMap<String, Object> injectionContext = new HashMap<>();
                injectionContext.put("tile", tile);
                TileView tileView = new TileView(injectionContext::get);
                final int xx = x, yy = y;
                tileView.getViewAsync(view -> grid.add(view, xx, yy));
            }
        }
    }
}
