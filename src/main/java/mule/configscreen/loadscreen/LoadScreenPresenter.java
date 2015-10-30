package mule.configscreen.loadscreen;

import com.airhacks.afterburner.injection.Injector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import mule.*;
import mule.mainscreen.MainScreenView;
import mule.player.Player;
import mule.world.map.Map;
import mvp.Presenter;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Presenter for the Loading screen.
 *
 * This is the screen where you can restore previously saved games.
 */
public class LoadScreenPresenter implements Presenter {
    @Inject private GameState gameState;
    @Inject private Database database;
    @Inject private Stage primaryStage;
    @Inject private KeyHandler keyHandler;

    @FXML private TableView<GameState> gamesTableView;

    @Override
    public void initialize() {
        ObservableList<GameState> games = gamesTableView.getItems();
        games.add(new GameState());
        games.add(gameState);
        System.out.println(database);
    }

    public void load(ActionEvent actionEvent) {
        //get selected row id
        int gameId = 1;

        Statement statement = database.getStatement();

        //TODO: use these values to do something
        try {
            ResultSet game = statement.executeQuery(String.format("SELECT * FROM game_data WHERE id=%d", gameId));
            if (game.next()) {
                int roundNumber = game.getInt("round_number");

                ResultSet playersRs = statement.executeQuery(String.format("SELECT * FROM player WHERE game_id=%d", gameId));
                ResultSet count = statement.executeQuery(String.format("SELECT COUNT(*) FROM player WHERE game_id=%d", gameId));
                Player players[] = new Player[count.getInt(0)];

                int i = 0;
                while (playersRs.next()) {
                    Player newPlayer = new Player();
                    newPlayer.getNameProp().setValue(playersRs.getString("name"));
                    players[i] = newPlayer;
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to load game");
        }
    }

    /**
     * Starts a new game from a previous state.
     *
     * @param gameState the loaded game's GameState.
     * @param turnManager the loaded game's TurnManager
     * @param map the loaded game's Map
     */
    private void startGame(GameState gameState, TurnManager turnManager, Map map) {
        // Disable land selection phase.
        LandSelectionManager lsMan = new LandSelectionManager();
        lsMan.getInLandSelectionPhaseProp().set(false);

        Injector.setModelOrService(gameState.getClass(), gameState);
        Injector.setModelOrService(turnManager.getClass(), turnManager);
        Injector.setModelOrService(map.getClass(), map);
        Injector.setModelOrService(lsMan.getClass(), lsMan);

        new MainScreenView().getViewAsync(view -> {
            Scene scene = new Scene(view);
            scene.addEventHandler(EventType.ROOT, keyHandler::handle);
            primaryStage.setScene(scene);
        });
    }
}
