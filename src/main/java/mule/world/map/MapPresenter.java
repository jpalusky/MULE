package mule.world.map;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mule.GameState;
import mule.KeyHandler;
import mule.mainscreen.MainContainerProvider;
import mule.player.Player;
import mule.world.map.tile.Tile;
import mule.world.map.tile.TilePresenter;
import mule.world.map.tile.TileType;
import mule.world.map.tile.TileView;
import mule.world.town.TownView;
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

    private void startGame() {
//        Player player = gameState.getCurrentPlayer();
//        map.getTile(player.getX(), player.getY()).getPresenter().addPlayer(player);
//        keyHandler.bind(KeyCode.UP, e -> {
//            Player p = gameState.getCurrentPlayer();
//            map.getTile(p.getX(), p.getY()).getPresenter().removePlayer();
//            p.moveUp();
//            map.getTile(p.getX(), p.getY()).getPresenter().addPlayer(p);
//        });
//        keyHandler.bind(KeyCode.DOWN, e -> {
//            Player p = gameState.getCurrentPlayer();
//            map.getTile(p.getX(), p.getY()).getPresenter().removePlayer();
//            p.moveDown();
//            map.getTile(p.getX(), p.getY()).getPresenter().addPlayer(p);
//        });
//        keyHandler.bind(KeyCode.LEFT, e -> {
//            Player p = gameState.getCurrentPlayer();
//            map.getTile(p.getX(), p.getY()).getPresenter().removePlayer();
//            p.moveLeft();
//            map.getTile(p.getX(), p.getY()).getPresenter().addPlayer(p);
//        });
//        keyHandler.bind(KeyCode.RIGHT, e -> {
//            Player p = gameState.getCurrentPlayer();
//            map.getTile(p.getX(), p.getY()).getPresenter().removePlayer();
//            p.moveRight();
//            map.getTile(p.getX(), p.getY()).getPresenter().addPlayer(p);
//        });
//
//        // Bind enter to enter town.
//        keyHandler.bind(KeyCode.ENTER, e -> {
//            Player p = gameState.getCurrentPlayer();
//            if (p.getX() != 4 || p.getY() != 2)  return;
//
//            Pane mainContainer = mainContainerProvider.get();
//            System.out.println(mainContainer);
//            mainContainer.getChildren().set(0, new TownView().getView());
//        });
    }
}
