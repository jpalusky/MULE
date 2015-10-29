package mule.mainscreen.statusbar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import mule.Database;
import mule.GameState;
import mule.LandSelectionManager;
import mule.TurnManager;
import mule.mainscreen.statusbar.playerstatus.PlayerStatusView;
import mule.player.Player;
import mule.world.map.Map;
import mule.world.map.tile.Tile;
import mvp.Presenter;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatusBarPresenter implements Presenter {
    @Inject private GameState gameState;
    @Inject private LandSelectionManager lsMan;
    @Inject private TurnManager turnManager;
    @Inject private Database database;
    @Inject private Map map;

    // Info for land selection phase.
    @FXML private Node lsBox;
    @FXML private Text lsCurrentPlayer;
    @FXML private Text lsCurrentRound;

    @FXML private Pane playerStats;

    // Stuff for displaying turn info.
    @FXML private Node turnBox;
    @FXML private Text timeLeftText;
    @FXML private Text turnCurrentPlayer;

    @Override
    public void initialize() {
        lsBox.visibleProperty().bind(lsMan.getInLandSelectionPhaseProp());
        lsBox.managedProperty().bind(lsMan.getInLandSelectionPhaseProp());

        lsCurrentPlayer.textProperty().bindBidirectional(lsMan.getCurrentPlayerProp(),
                new StringConverter<Player>() {
                    @Override
                    public String toString(Player player) {
                        return player.getName();
                    }
                    @Override
                    public Player fromString(String string) {
                        return null;
                    }
                });
        lsCurrentRound.textProperty().bindBidirectional(lsMan.getCurrentRoundProp(),
                new NumberStringConverter());

        // Add player info to status bar
        for (Player player : gameState.getPlayers()) {
            PlayerStatusView playerStatView = new PlayerStatusView(p -> player);
            playerStatView.getViewAsync(playerStats.getChildren()::add);
        }

        // Show information relevant to the turn.
        turnBox.visibleProperty().bind(lsMan.getInLandSelectionPhaseProp().not());
        turnBox.managedProperty().bind(lsMan.getInLandSelectionPhaseProp().not());
        timeLeftText.textProperty().bindBidirectional(turnManager.getTimeLeftProp(),
                new NumberStringConverter());
        turnCurrentPlayer.textProperty().bindBidirectional(turnManager.getCurrentPlayerProp(),
                new StringConverter<Player>() {
                    @Override
                    public String toString(Player player) {
                        return player == null ? "" : player.getName();
                    }
                    @Override
                    public Player fromString(String string) {
                        return null;
                    }
                });
        System.out.println(turnCurrentPlayer);
    }

    public void save(ActionEvent actionEvent) {
        Statement statement = database.getStatement();
        try {
            //Save game
            statement.execute("INSERT INTO game_data (round_number) VALUES (" + turnManager.getRoundNumber() + ")");

            int gameId = database.getMaxID(statement, "game_data");

            //Save players
            Player[] players = gameState.getPlayers();
            for (Player player : players) {
                String sql = String.format("INSERT INTO player (game_id, food, energy, ore, loc_x, loc_y, race_id, color_id, money, name) VALUES (%d, %d, %d, %d, %d, %d, %d, %d, %d, \"%s\")",
                        gameId,
                        player.getFood(),
                        player.getEnergy(),
                        player.getOre(),
                        player.getMapLocation().x,
                        player.getMapLocation().y,
                        player.getRace().ordinal(),
                        player.getColor().ordinal(),
                        player.getMoney(),
                        player.getName()
                );
                System.out.println(sql);
                statement.execute(sql);

                int playerId = database.getMaxID(statement, "player");

                for (Tile tile : player.getProperties()) {
//                    sql = String.format("INSERT INTO player_tile (player_id, mule_type, number) VALUES (%d, %d, %d)", playerId, tile.getMuleProp().get().ordinal());
//                    statement.execute(sql);
                }

            }
//
//            Tile tiles[][] = map.getTiles();
//            for (int i = 0; i < tiles.length; i++) {
//                for (int j = 0; j < tiles[0].length; j++) {
//                    int playerId = -1;
//                    if (tiles[i][j].getPlayerProp().get() != null) {
//
//                    }
//                    String sql = String.format("INSERT INTO tile (player_id, mule_type, number) VALUES (%d, %d, %d)", playerId, tiles[i][j].getMuleProp().get().ordinal(), i + j);
//                    statement.execute(sql);
//                }
//            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Could not save game");
        }
    }
}
