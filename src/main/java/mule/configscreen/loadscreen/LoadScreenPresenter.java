package mule.configscreen.loadscreen;

import com.airhacks.afterburner.injection.Injector;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import mule.*;
import mule.mainscreen.MainScreenView;
import mule.player.Color;
import mule.player.Player;
import mule.player.Race;
import mule.world.map.Map;
import mule.world.map.MapType;
import mule.world.map.tile.Tile;
import mule.world.map.tile.TileType;
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
    @Inject private Database database;
    @Inject private Stage primaryStage;
    @Inject private KeyHandler keyHandler;

    @FXML private Button loadButton;
    @FXML private TableView<GameState> gamesTableView;
    @FXML private TableColumn<GameState, Integer> gameNumberColumn;
    @FXML private TableColumn<GameState, String> gameDateColumn;
    @FXML private TableColumn<GameState, Integer> gameRoundNumberColumn;
    @FXML private TableColumn<GameState, String> difficultyColumn;
    @FXML private TableColumn<GameState, String> mapTypeColumn;

    @Override
    public void initialize() {
        ObservableList<GameState> games = gamesTableView.getItems();

        loadButton.disableProperty().bind(Bindings.size(gamesTableView.getSelectionModel().getSelectedItems()).lessThan(1));
        //query database
        addGamesFromDatabase(games);

        gameNumberColumn.setCellValueFactory(c -> c.getValue().getGameNumberProperty().asObject());
        gameDateColumn.setCellValueFactory(c -> c.getValue().getDateProperty());
        gameRoundNumberColumn.setCellValueFactory(c -> c.getValue().getRoundNumberProperty().asObject());
        difficultyColumn.setCellValueFactory(c -> c.getValue().getDifficultyProperty());
        mapTypeColumn.setCellValueFactory(c -> c.getValue().getMapTypeProperty());
        System.out.println(database);
    }

    public void addGamesFromDatabase(ObservableList<GameState> games) {
        Statement statement = database.getStatement();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM game_data");
            while(rs.next()) {
                games.add(new GameState(rs.getInt("id"), rs.getString("date"), rs.getInt("round_number"), rs.getInt("difficulty"), rs.getInt("map_type")));
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
                int diffIndex = game.getInt("difficulty");
                int mapIndex = game.getInt("map_type");
                int roundNumber = game.getInt("round_number");
                if (game.next()) {
                    System.out.println("Game found");
                    ResultSet count = statement.executeQuery(String.format("SELECT COUNT(*) FROM player WHERE game_id=%d", gameId));
                    int numPlayers = count.getInt("COUNT(*)");
                    Player players[] = new Player[numPlayers];


                    //Load map
                    String sql = String.format("SELECT * FROM map_tile WHERE game_id=%d", gameId);
                    ResultSet mapRs = statement.executeQuery(sql);

                    TileType[][] tiles = new TileType[5][9];
                    TileType[] values = TileType.values();

                    while (mapRs.next()) {
                        int x = mapRs.getInt("x");
                        int y = mapRs.getInt("y");

                        tiles[y][x] = values[mapRs.getInt("type")];
                    }

                    Map map = new Map();
                    map.initialize(tiles);

                    Tile[][] mapTiles = map.getTiles();

                    //Load player
                    ResultSet playersRs = statement.executeQuery(String.format("SELECT * FROM player WHERE game_id=%d", gameId));
//                    while (playersRs.next()) {
//                        System.out.println("PLAYER: " + playersRs.getString("name"));
//                    }
                    Statement mapStmt = database.getStatement();
                    int i = 0;
                    while (playersRs.next()) {
                        int id = playersRs.getInt("id");
                        Player newPlayer = new Player(
                                playersRs.getString("name"),
                                Color.values()[playersRs.getInt("color_id")],
                                Race.values()[playersRs.getInt("race_id")],
                                playersRs.getInt("money"),
                                playersRs.getInt("food"),
                                playersRs.getInt("energy"),
                                playersRs.getInt("ore")
                        );
                        players[i++] = newPlayer;

                        sql = String.format("SELECT * FROM player_tile WHERE player_id=%d", id);
                        ResultSet playerMapRs = mapStmt.executeQuery(sql);
                        while (playerMapRs.next()) {
                            int x = playerMapRs.getInt("x");
                            int y = playerMapRs.getInt("y");
                            mapTiles[y][x].getMuleProp().set(MuleType.values()[playerMapRs.getInt("mule_type")]);
                            mapTiles[y][x].setOwner(newPlayer);
                            newPlayer.addProperty(mapTiles[y][x]);
                        }
                    }

                    playersRs.close();
                    map.setTiles(mapTiles);

                    //Load game state
                    TurnManager turnManager = new TurnManager(roundNumber);
                    GameState newGameState = new GameState(players, Difficulty.values()[diffIndex], MapType.values()[mapIndex]);

                    //Load store
                    sql = String.format("SELECT * FROM store WHERE game_id=%d", gameId);
                    ResultSet storeRs = statement.executeQuery(sql);
                    Store store = new Store(storeRs.getInt("food"), storeRs.getInt("energy"), storeRs.getInt("ore"), storeRs.getInt("mule"));

                    startGame(newGameState, turnManager, map, store);
                } else {
                    System.out.println("No matching game found.");
                }
            } catch (SQLException e) {
                System.out.println("Unable to load game");
                e.printStackTrace();
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
        Injector.setModelOrService(gameState.getClass(), gameState);

        // Disable land selection phase.
        LandSelectionManager lsMan = Injector.instantiateModelOrService(LandSelectionManager.class);
        lsMan.getInLandSelectionPhaseProp().set(false);

        Injector.setModelOrService(map.getClass(), map);
        Injector.setModelOrService(store.getClass(), store);
        Injector.setModelOrService(turnManager.getClass(), turnManager);

        Injector.injectMembers(turnManager.getClass(), turnManager);

        new MainScreenView().getViewAsync(view -> {
            gamesTableView.getScene().getWindow().hide();
            Scene scene = new Scene(view);
            scene.addEventHandler(EventType.ROOT, keyHandler::handle);
            primaryStage.setScene(scene);
            turnManager.start();
        });
    }
}
