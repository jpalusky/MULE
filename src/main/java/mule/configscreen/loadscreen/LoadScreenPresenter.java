package mule.configscreen.loadscreen;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import com.airhacks.afterburner.injection.Injector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import mule.Database;
import mule.Difficulty;
import mule.GameState;
import mule.TurnManager;
import javafx.stage.Stage;
import mule.*;
import mule.mainscreen.MainScreenView;
import mule.player.Player;
import mule.world.map.Map;
import mule.world.map.MapType;
import mule.world.map.tile.TileType;
import mule.world.map.Map;
import mule.world.town.store.Store;
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

    @FXML private Button loadButton;
    @FXML private TableView<GameState> gamesTableView;
    @FXML private TableColumn<GameState, Integer> gameNumberColumn;
    @FXML private TableColumn<GameState, String> gameDateColumn;
    @FXML private TableColumn<GameState, Integer> gameRoundNumberColumn;

    @Override
    public void initialize() {
        ObservableList<GameState> games = gamesTableView.getItems();

        loadButton.disableProperty().bind(Bindings.size(gamesTableView.getSelectionModel().getSelectedItems()).greaterThan(0));
        //query database
        addGamesFromDatabase(games);

        gameNumberColumn.setCellValueFactory(c -> c.getValue().getGameNumberProperty().asObject());
        gameDateColumn.setCellValueFactory(c -> c.getValue().getDateProperty());
        gameRoundNumberColumn.setCellValueFactory(c -> c.getValue().getRoundNumberProperty().asObject());
        System.out.println(database);
    }

    public void addGamesFromDatabase(ObservableList<GameState> games) {
        Statement statement = database.getStatement();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM game_data");
            while(rs.next()) {
                games.add(new GameState(rs.getInt("id"), rs.getString("date"), rs.getInt("round_number")));
            }
        } catch (SQLException e) {
            System.out.printf("Could not load game list.");
        }
    }

    public void load(ActionEvent actionEvent) {
        //get selected row id

        if (!gamesTableView.getSelectionModel().isEmpty()) {
            GameState selected = gamesTableView.getSelectionModel().getSelectedItem();
            System.out.println("selected: " + selected.getGameNumberProperty().toString());

            Statement statement = database.getStatement();
            int gameId = selected.getGameNumberProperty().get();

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

                    int diffIndex = 0;
                    int mapIndex = 0;
                    TurnManager turnManager = new TurnManager(roundNumber);
                    GameState newGameState = new GameState(players, Difficulty.values()[diffIndex], MapType.values()[mapIndex]);
                    TileType[][] tiles = new TileType[9][5];


                    Map map = new Map();
                    map.initialize(tiles);

//                    this.startGame(newGameState, turnManager, map);
                } else {
                    System.out.println("No matching game found.");
                }
            } catch (SQLException e) {
                System.out.println("Unable to load game");
            }

        } else {
            System.out.println("No selection.");
        }
    }


    /**
     * Starts a new game from a previous state.
     *
     * @param gameState the loaded game's GameState.
     * @param turnManager the loaded game's TurnManager
     * @param map the loaded game's Map
     * @param store the Store model
     */
    private void startGame(GameState gameState, TurnManager turnManager, Map map, Store store) {
        // Disable land selection phase.
        LandSelectionManager lsMan = new LandSelectionManager();
        lsMan.getInLandSelectionPhaseProp().set(false);

        Injector.setModelOrService(gameState.getClass(), gameState);
        Injector.setModelOrService(turnManager.getClass(), turnManager);
        Injector.setModelOrService(map.getClass(), map);
        Injector.setModelOrService(lsMan.getClass(), lsMan);
        Injector.setModelOrService(store.getClass(), store);

        new MainScreenView().getViewAsync(view -> {
            Scene scene = new Scene(view);
            scene.addEventHandler(EventType.ROOT, keyHandler::handle);
            primaryStage.setScene(scene);
        });
    }
}
