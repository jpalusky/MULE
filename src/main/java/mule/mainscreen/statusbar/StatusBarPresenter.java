package mule.mainscreen.statusbar;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import mule.GameState;
import mvp.Presenter;

import javax.inject.Inject;

public class StatusBarPresenter implements Presenter {
    @Inject GameState gameState;

    @FXML private Text currentPlayer;
    @FXML private Text currentRound;
    @FXML private Text playerMoney;

    @Override
    public void initialize() {
        currentPlayer.textProperty().bind(gameState.getCurrentPlayerProp());
        Bindings.bindBidirectional(currentRound.textProperty(),
                gameState.getCurrentRoundProp(), new NumberStringConverter());
        Bindings.bindBidirectional(playerMoney.textProperty(),
                gameState.getCurrentPlayerMoneyProp(), new NumberStringConverter());
    }
}
