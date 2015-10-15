package mule;

import mule.player.Player;
import mule.world.map.MapType;

import java.util.Arrays;

/**
 * Global class containing all gamestate data.
 */
public class GameState {
    private Difficulty difficulty;
    private MapType mapType;

    private Player[] players;

    // Empty constructor for injector.
    public GameState() {
    }

    public GameState(Player[] players, Difficulty difficulty, MapType mapType) {
        this.players = players;
        this.mapType = mapType;
        this.difficulty = difficulty;
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

    @Override
    public String toString() {
        return String.format("Difficulty: %s\n, Map type: %s\n Players: %s\n",
                difficulty, mapType, Arrays.toString(players));
    }
}
