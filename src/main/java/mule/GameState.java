package mule;

import javafx.beans.property.SimpleIntegerProperty;
import mule.player.Player;
import mule.world.map.MapType;

import java.util.Arrays;

/**
 * Global class containing all gamestate data.
 */
public class GameState {
    private Difficulty difficulty;
    private MapType mapType;

    private SimpleIntegerProperty roundNumberProperty;

    private Player[] players;

    // Empty constructor for injector.
    public GameState() {
    }

    public GameState(Player[] players, Difficulty difficulty, MapType mapType) {
        this.players = players;
        this.mapType = mapType;
        this.difficulty = difficulty;
//        roundNumberProperty = new SimpleIntegerProperty(2);
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNumPlayers() {
        return players.length;
    }

    public MapType getMapType() {
        return mapType;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return String.format("Difficulty: %s\n, Map type: %s\n Players: %s\n",
                difficulty, mapType, Arrays.toString(players));
    }
}
