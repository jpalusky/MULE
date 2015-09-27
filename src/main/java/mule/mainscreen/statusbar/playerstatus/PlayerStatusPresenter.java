package mule.mainscreen.statusbar.playerstatus;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;

public class PlayerStatusPresenter implements Presenter{
    @Inject private Player player;

    @FXML private Text playerName;
    @FXML private Text money;

    @Override
    public void initialize() {
        playerName.textProperty().bind(player.getNameProp());
        playerName.setFill(player.getColor().getColor());
        money.textProperty().bindBidirectional(player.getMoneyProp(),
                new NumberStringConverter());
    }
}
