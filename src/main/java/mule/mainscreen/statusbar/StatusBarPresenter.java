package mule.mainscreen.statusbar;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import mule.GameState;
import mule.LandSelectionManager;
import mule.RoundManager;
import mule.mainscreen.statusbar.playerstatus.PlayerStatusView;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;

public class StatusBarPresenter implements Presenter {
    @Inject private GameState gameState;
    @Inject private LandSelectionManager lsMan;
    @Inject private RoundManager roundManager;

    // Info for land selection phase.
    @FXML private Node lsBox;
    @FXML private Text lsCurrentPlayer;
    @FXML private Text lsCurrentRound;
    @FXML private Label lsTitle;

    @FXML private Pane playerStats;

    // Stuff for displaying time left.
    @FXML private Label timeLeftLabel;
    @FXML private Label nextTurnLabel;
    @FXML private Text timeLeftText;

    @Override
    public void initialize() {
        lsBox.visibleProperty().bind(lsMan.getInLandSelectionPhaseProp());
        lsBox.managedProperty().bind(lsMan.getInLandSelectionPhaseProp());

        lsTitle.visibleProperty().bind(lsMan.getInLandSelectionPhaseProp());
        lsTitle.managedProperty().bind(lsMan.getInLandSelectionPhaseProp());

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

        // Show time left in turn.
        nextTurnLabel.visibleProperty().bind(roundManager.getIsWaitingProp());
        timeLeftLabel.visibleProperty().bind(lsMan.getInLandSelectionPhaseProp().not());
        timeLeftLabel.managedProperty().bind(lsMan.getInLandSelectionPhaseProp().not());
        timeLeftText.textProperty().bindBidirectional(roundManager.getTurnManager().getTimeLeftProp(),
                new NumberStringConverter());
    }
}
