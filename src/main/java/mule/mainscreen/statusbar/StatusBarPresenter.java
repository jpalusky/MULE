package mule.mainscreen.statusbar;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import mule.GameState;
import mule.LandSelectionManager;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;

public class StatusBarPresenter implements Presenter {
    @Inject private GameState gameState;
    @Inject private LandSelectionManager lsMan;

    // Info for land selection phase.
    @FXML private Node lsBox;
    @FXML private Text lsCurrentPlayer;
    @FXML private Text lsCurrentRound;

    @Override
    public void initialize() {
        lsBox.visibleProperty().bind(lsMan.getInLandSelectionPhaseProp());
        lsBox.managedProperty().bind(lsMan.getInLandSelectionPhaseProp());
        lsCurrentPlayer.textProperty().bindBidirectional( lsMan.getCurrentPlayerProp(),
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
    }
}
