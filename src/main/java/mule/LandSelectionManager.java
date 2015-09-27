package mule;

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

    private boolean inLandSelectionPhase;
    private int counter;
    private Player[] players;
    private int numPassed;

    public LandSelectionManager() {
        inLandSelectionPhase = true;
    }

    @PostConstruct
    private void init() {
        players = gameState.getPlayers();
        keyHandler.bind(KeyCode.P, e -> playerPass());
    }

    public void manage(Tile tile) {
        if (!inLandSelectionPhase) return;

        if (getCurrentPlayer().buyProperty(tile))
            nextPlayer();
    }

    private Player getCurrentPlayer() {
        return players[counter % players.length];
    }

    private void nextPlayer() {
        ++counter;
        if (counter % players.length == 0) numPassed = 0;
    }

    private void playerPass() {
        if (++numPassed == players.length) {
            inLandSelectionPhase = false;
            keyHandler.unbind(KeyCode.P);
        }
        nextPlayer();
    }
}
