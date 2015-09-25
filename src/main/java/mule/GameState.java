package mule;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mule.player.Player;

import java.util.Arrays;

/**
 * Global class containing all gamestate data.
 */
public class GameState {
    private Difficulty difficulty;
    private MapType mapType;

    /** A counter that gets incremented every time the player switches. */
    private int counter;
    private Player[] players;

    private StringProperty currentPlayerProp;
    private IntegerProperty currentRoundProp;

    private boolean inSelectionPhase;

    /**
     * The number of player that has passed the last round.
     * This is kept tracked of because if all players in a
     * round has passed, then the land selection phase ends.
     */
    private int numPassed;

    public void configure(Player[] players, Difficulty difficulty, MapType mapType) {
        this.players = players;
        this.mapType = mapType;
        this.difficulty = difficulty;
        inSelectionPhase = true;
        currentPlayerProp = new SimpleStringProperty();
        currentRoundProp = new SimpleIntegerProperty();
        updateProps();
    }

    /**
     * Update all Properties based on game state.
     */
    private void updateProps() {
        currentPlayerProp.setValue(getCurrentPlayer().getName());
        currentRoundProp.setValue(getRound());
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNumPlayers() {
        return players.length;
    }

    public Player getCurrentPlayer() {
        return players[counter % players.length];
    }

    public StringProperty getCurrentPlayerProp() {
        return currentPlayerProp;
    }

    public int getRound() {
        return counter / players.length;
    }

    public IntegerProperty getCurrentRoundProp() {
        return currentRoundProp;
    }

    public void incCounter() {
        ++counter;
        if (counter % players.length == 0) numPassed = 0;
        updateProps();
    }

    public void incNumPassed() {
        ++numPassed;
    }

    public int getNumPassed() {
        return numPassed;
    }

    public boolean getInSelectionPhase() {
        return inSelectionPhase;
    }

    public void setInSelectionPhase(boolean value) {
        inSelectionPhase = value;
    }

    @Override
    public String toString() {
        return String.format("Difficulty: %s\n, Map type: %s\n Players: %s\n",
                difficulty, mapType, Arrays.toString(players));
    }
}
