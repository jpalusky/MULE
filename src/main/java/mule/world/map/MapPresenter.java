package mule.world.map;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mule.GameState;
import mule.KeyHandler;
import mule.mainscreen.MainContainerProvider;
import mule.player.Player;
import mule.world.map.tile.TileType;
import mule.world.map.tile.TileView;
import mule.world.town.TownView;
import mvp.Presenter;

import javax.inject.Inject;

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
        // TODO: Allow map switching for randomly generated maps.
        TileType[][] mapType = standardMap;

        map.initializeMap(mapType);
        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 5; ++y) {
                TileView tile = map.getTile(x, y);
                grid.add(tile.getView(), x, y);
                tile.getPresenter().getRoot().setOnMouseClicked(e -> handleLandSelection(tile));
            }
        }

        // Pass land selection
        keyHandler.bind(KeyCode.P, e -> {
            if (!gameState.getInSelectionPhase()) return;

            gameState.incNumPassed();
            if (gameState.getNumPassed() == gameState.getNumPlayers()) {
                gameState.setInSelectionPhase(false);
                gameState.incCounter();
                startGame();
                return;
            }
            gameState.incCounter();
        });

    }

    private void handleLandSelection(TileView tile) {
        if (!gameState.getInSelectionPhase()) return;

        boolean isFree = gameState.getRound() < 2;

        if (tile.getPresenter().setOwner(gameState.getCurrentPlayer())) {
            gameState.incCounter();
        }
    }

    private void startGame() {
        Player player = gameState.getCurrentPlayer();
        map.getTile(player.getX(), player.getY()).getPresenter().addPlayer(player);
        keyHandler.bind(KeyCode.UP, e -> {
            Player p = gameState.getCurrentPlayer();
            map.getTile(p.getX(), p.getY()).getPresenter().removePlayer();
            p.moveUp();
            map.getTile(p.getX(), p.getY()).getPresenter().addPlayer(p);
        });
        keyHandler.bind(KeyCode.DOWN, e -> {
            Player p = gameState.getCurrentPlayer();
            map.getTile(p.getX(), p.getY()).getPresenter().removePlayer();
            p.moveDown();
            map.getTile(p.getX(), p.getY()).getPresenter().addPlayer(p);
        });
        keyHandler.bind(KeyCode.LEFT, e -> {
            Player p = gameState.getCurrentPlayer();
            map.getTile(p.getX(), p.getY()).getPresenter().removePlayer();
            p.moveLeft();
            map.getTile(p.getX(), p.getY()).getPresenter().addPlayer(p);
        });
        keyHandler.bind(KeyCode.RIGHT, e -> {
            Player p = gameState.getCurrentPlayer();
            map.getTile(p.getX(), p.getY()).getPresenter().removePlayer();
            p.moveRight();
            map.getTile(p.getX(), p.getY()).getPresenter().addPlayer(p);
        });

        // Bind enter to enter town.
        keyHandler.bind(KeyCode.ENTER, e -> {
            Player p = gameState.getCurrentPlayer();
            if (p.getX() != 4 || p.getY() != 2)  return;

            Pane mainContainer = mainContainerProvider.get();
            System.out.println(mainContainer);
            mainContainer.getChildren().set(0, new TownView().getView());
        });
    }
}
