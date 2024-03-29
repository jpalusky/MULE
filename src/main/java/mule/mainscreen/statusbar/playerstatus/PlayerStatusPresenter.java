package mule.mainscreen.statusbar.playerstatus;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import mule.MuleType.MuleStringConverter;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;

public class PlayerStatusPresenter implements Presenter {
    @Inject private Player player;

    @FXML private Text playerName;
    @FXML private Text money;
    @FXML private Text food;
    @FXML private Text energy;
    @FXML private Text ore;
    @FXML private Text mule;

    @Override
    public void initialize() {
        playerName.textProperty().bind(player.getNameProp());
        playerName.setFill(player.getColor().getColor());

        money.textProperty().bindBidirectional(player.getMoneyProp(),
                new NumberStringConverter());
        food.textProperty().bindBidirectional(player.getFoodProp(),
                new NumberStringConverter());
        energy.textProperty().bindBidirectional(player.getEnergyProp(),
                new NumberStringConverter());
        ore.textProperty().bindBidirectional(player.getOreProp(),
                new NumberStringConverter());
        mule.textProperty().bindBidirectional(player.getMuleProp(),
                new MuleStringConverter());
    }
}
