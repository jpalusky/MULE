package mule.configscreen.loadscreen;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import mule.Database;
import mule.GameState;
import mule.player.Player;
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

    @FXML private TableView<GameState> gamesTableView;

    @Override
    public void initialize() {
        ObservableList<GameState> games = gamesTableView.getItems();
        games.add(new GameState());
        games.add(gameState);
        System.out.println(database);
    }

    @Override
    public boolean isValid() {
        return false;
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
}
