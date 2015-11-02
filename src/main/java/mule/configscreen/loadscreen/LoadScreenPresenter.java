package mule.configscreen.loadscreen;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import mule.Database;
import mule.Difficulty;
import mule.GameState;
import mule.TurnManager;
import mule.player.Player;
import mule.world.map.Map;
import mule.world.map.MapType;
import mule.world.map.tile.TileType;
import mvp.Presenter;
import mvp.Validateable;

import javax.inject.Inject;
import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;

/**
 * Created by david on 10/29/2015.
 */
public class LoadScreenPresenter implements Presenter, Validateable {
    @Inject private GameState gameState;
    @Inject private Database database;

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

    @Override
    public boolean isValid() {
        return false;
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

        if(!gamesTableView.getSelectionModel().isEmpty()) {
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
}
