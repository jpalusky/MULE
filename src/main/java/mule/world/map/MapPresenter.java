package mule.world.map;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import mule.GameState;
import mule.KeyHandler;
import mule.TurnManager;
import mule.mainscreen.MainScreen;
import mule.player.Player;
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
    @Inject private MainScreen mainScreen;
    @Inject private Map map;
    @Inject private TurnManager turnManager;
    @Inject private GameState gameState;
    @Inject private KeyHandler keyHandler;

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

        // Change player displayed on map when current player changes.
        turnManager.getCurrentPlayerProp().addListener((obs, oldPlayer, currentPlayer) -> {
            if (oldPlayer != null) {
                map.getTile(oldPlayer.getMapLocation()).removePlayer();
            }

            if (currentPlayer != null) {
                map.getTile(currentPlayer.getMapLocation()).addPlayer(currentPlayer);
            }
        });

        // Add event listeners for player movement.
        for (Player player : gameState.getPlayers()) {
            player.getMapLocationProp().addListener((obs2, oldPoint, point) -> {
                map.getTile(oldPoint).removePlayer();
                map.getTile(point).addPlayer(player);
            });
        }
    }

    /**
     * Bind all user input for the Map.
     */
    public void bindInput() {
        // Catch ENTER to enter town.
        keyHandler.bind(KeyCode.ENTER, e -> {
            Player player = turnManager.getCurrentPlayer();

            if (player != null) {
                // TODO: detect town instead of using hardcoded coordinates.
                if (player.getMapLocation().equals(new Point(4, 2))) {
                    player.enterTown();
                    mainScreen.showTown();
                }
            }
        });
    }

    public void unbindInput() {
        keyHandler.unbind(KeyCode.ENTER);
    }
}
