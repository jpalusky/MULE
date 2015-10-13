package mule.mainscreen.statusbar;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import mule.GameState;
import mule.LandSelectionManager;
import mule.TurnManager;
import mule.mainscreen.statusbar.playerstatus.PlayerStatusView;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;

public class StatusBarPresenter implements Presenter {
    @Inject private GameState gameState;
    @Inject private LandSelectionManager lsMan;
    @Inject private TurnManager turnManager;

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
}
