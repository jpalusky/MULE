package mule.world.town.store;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import mule.GameState;
import mule.MuleType;
import mule.MuleType.MuleStringConverter;
import mule.TurnManager;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;

public class StorePresenter implements Presenter{
    @Inject Store store;
    @Inject TurnManager turnManager;
    @Inject GameState gameState;
    @Inject Pane parent;

    @FXML private Button foodBuy, foodSell;
    @FXML private Button energyBuy, energySell;
    @FXML private Button oreBuy, oreSell;
    @FXML private Text muleNumber;
    @FXML private Button muleSell, foodMule, energyMule, oreMule;
    @FXML private Button exitButton;

    public void initialize() {
        store.configureDefaults(gameState.getDifficulty());

        // Bind store resources.
        foodBuy.textProperty().bindBidirectional(store.getFoodProp(), new NumberStringConverter());
        energyBuy.textProperty().bindBidirectional(store.getEnergyProp(), new NumberStringConverter());
        oreBuy.textProperty().bindBidirectional(store.getOreProp(), new NumberStringConverter());
        muleNumber.textProperty().bindBidirectional(store.getMuleProp(), new NumberStringConverter());

        // Bind player resources.
        bindPlayerResources(turnManager.getCurrentPlayer());
        turnManager.getCurrentPlayerProp().addListener((obs, oldPlayer, newPlayer) -> {
            unbindPlayerResources(oldPlayer);
            bindPlayerResources(newPlayer);
        });

        // Bind the buy/sell buttons event handlers.
        foodBuy.setOnMouseClicked(e -> store.sellFood(turnManager.getCurrentPlayer()));
        foodSell.setOnMouseClicked(e -> store.buyFood(turnManager.getCurrentPlayer()));
        energyBuy.setOnMouseClicked(e -> store.sellEnergy(turnManager.getCurrentPlayer()));
        energySell.setOnMouseClicked(e -> store.buyEnergy(turnManager.getCurrentPlayer()));
        oreBuy.setOnMouseClicked(e -> store.sellOre(turnManager.getCurrentPlayer()));
        oreSell.setOnMouseClicked(e -> store.buyOre(turnManager.getCurrentPlayer()));
        foodMule.setOnMouseClicked(e -> store.sellMule(turnManager.getCurrentPlayer(), MuleType.FOOD));
        energyMule.setOnMouseClicked(e -> store.sellMule(turnManager.getCurrentPlayer(), MuleType.ENERGY));
        oreMule.setOnMouseClicked(e -> store.sellMule(turnManager.getCurrentPlayer(), MuleType.ORE));
        muleSell.setOnMouseClicked(e -> store.buyMule(turnManager.getCurrentPlayer()));

        // Bind Exit button event handler.
        exitButton.setOnMouseClicked(e -> parent.getChildren().clear());
    }

    private void bindPlayerResources(Player player) {
        foodSell.textProperty().bindBidirectional(player.getFoodProp(), new NumberStringConverter());
        energySell.textProperty().bindBidirectional(player.getEnergyProp(), new NumberStringConverter());
        oreSell.textProperty().bindBidirectional(player.getOreProp(), new NumberStringConverter());
        muleSell.textProperty().bindBidirectional(player.getMuleProp(), new MuleStringConverter());
    }

    private void unbindPlayerResources(Player player) {
        if (player == null) return;
        foodSell.textProperty().unbindBidirectional(player.getFoodProp());
        energySell.textProperty().unbindBidirectional(player.getEnergyProp());
        oreSell.textProperty().unbindBidirectional(player.getOreProp());
        muleSell.textProperty().unbindBidirectional(player.getMuleProp());
    }
}
