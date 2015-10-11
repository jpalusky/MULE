package mule;

import javafx.beans.property.*;
import javafx.scene.input.KeyCode;
import mule.player.Player;
import mule.world.map.tile.Tile;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The manager for the game during the Land Selection Phase.
 *
 * The land selection phase starts at the beginning of the
 * game. Each player takes turn choosing property to buy.
 * For the first 2 rounds, the property is free.
 * When all the player passes in a single round,
 * the land selection phase ends.
 */
public class LandSelectionManager {
    @Inject private GameState gameState;
    @Inject private KeyHandler keyHandler;

    private BooleanProperty inLandSelectionPhase;
    private ObjectProperty<Player> currentPlayer;
    private IntegerProperty currentRound;

    private int counter;
    private int numPassed;

    public LandSelectionManager() {
        inLandSelectionPhase = new SimpleBooleanProperty(true);
        currentPlayer = new SimpleObjectProperty<>();
        currentRound = new SimpleIntegerProperty();
    }

    @PostConstruct
    private void init() {
        keyHandler.bind(KeyCode.P, e -> playerPass());
        currentPlayer.set(getCurrentPlayer());
    }

    public void manage(Tile tile) {
        if (!inLandSelectionPhase.get()) return;
        if (tile.isOwned()) return;

        Player player = getCurrentPlayer();

        if (isFree() || tile.getCost() <= player.getMoney()) {
            tile.setOwner(player);
            player.addProperty(tile);
            if (!isFree()) player.debitMoney(tile.getCost());
            nextPlayer();
//            while (getCurrentPlayer().getMoney() < tile.getMinimumCost()) {
//                playerPass();
//            }
        }
    }

    public BooleanProperty getInLandSelectionPhaseProp() {
        return inLandSelectionPhase;
    }

    public ObjectProperty<Player> getCurrentPlayerProp() {
        return currentPlayer;
    }

    public IntegerProperty getCurrentRoundProp() {
        return currentRound;
    }

    public boolean isFree() {
        return getRound() < 2;
    }

    private int getRound() {
        return counter / gameState.getNumPlayers();
    }

    private Player getCurrentPlayer() {
        return gameState.getPlayers()[counter % gameState.getNumPlayers()];
    }

    private void nextPlayer() {
        ++counter;
        if (counter % gameState.getPlayers().length == 0) numPassed = 0;
        currentPlayer.set(getCurrentPlayer());
        currentRound.set(getRound());
    }

    private void playerPass() {
        if (++numPassed == gameState.getNumPlayers()) {
            inLandSelectionPhase.set(false);
            keyHandler.unbind(KeyCode.P);
        }
        nextPlayer();
    }
}
