package mule.world.map;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import mule.GameState;
import mule.KeyHandler;
import mule.MuleType;
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
        TileType[][] mapType = gameState.getMapType() == MapType.STANDARD ?
                Map.getStandardMap() : Map.getRandomMap();
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
                if (player.getMapLocation().equals(new Point(4, 2))) {
                    player.enterTown();
                    mainScreen.showTown();
                }
            }
        });

        // Bind M to place mule.
        keyHandler.bind(KeyCode.M, e -> placeMule(turnManager.getCurrentPlayer()));

        // Bind movement
        keyHandler.bind(KeyCode.RIGHT, e -> turnManager.getCurrentPlayer().moveRight());
        keyHandler.bind(KeyCode.LEFT, e -> turnManager.getCurrentPlayer().moveLeft());
        keyHandler.bind(KeyCode.DOWN, e -> turnManager.getCurrentPlayer().moveDown());
        keyHandler.bind(KeyCode.UP, e -> turnManager.getCurrentPlayer().moveUp());
    }

    public void unbindInput() {
        keyHandler.unbind(KeyCode.ENTER, KeyCode.M, KeyCode.UP, KeyCode.DOWN, KeyCode.RIGHT, KeyCode.LEFT);
    }

    private void placeMule(Player player) {
        if (player == null) return;
        if (player.getMule() == MuleType.NONE) return;

        Tile tile = map.getTile(player.getMapLocation());

        MuleType mule = player.getMule();
        player.removeMule();

        if (player.owns(tile)) {
            tile.addMule(mule);
        }
    }
}
