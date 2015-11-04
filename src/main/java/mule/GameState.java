package mule;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import mule.player.Player;
import mule.world.map.MapType;

import java.util.Arrays;

/**
 * Global class containing all gamestate data.
 */
public class GameState {
    private Difficulty difficulty;
    private MapType mapType;

    private SimpleIntegerProperty gameNumberProperty;
    private SimpleIntegerProperty roundNumberProperty;
    private SimpleStringProperty difficultyProperty;
    private SimpleStringProperty mapTypeProperty;
    private SimpleStringProperty dateProperty;

    private Player[] players;

    // Empty constructor for injector.
    public GameState() {
    }

    public GameState(Player[] players, Difficulty difficulty, MapType mapType) {
        this.players = players;
        this.mapType = mapType;
        this.difficulty = difficulty;
        roundNumberProperty = new SimpleIntegerProperty();
        difficultyProperty = new SimpleStringProperty();
        mapTypeProperty = new SimpleStringProperty();
        gameNumberProperty = new SimpleIntegerProperty();
        dateProperty = new SimpleStringProperty();
    }

    //Used for the loading table view
    public GameState(int gameNumber, String date, int roundNumber, int mapType, int difficulty) {
        this.gameNumberProperty = new SimpleIntegerProperty(gameNumber);
        this.roundNumberProperty = new SimpleIntegerProperty(roundNumber);
        this.dateProperty = new SimpleStringProperty(date);
        this.difficultyProperty = new SimpleStringProperty(Difficulty.values()[difficulty].toString());
        this.mapTypeProperty = new SimpleStringProperty(MapType.values()[mapType].toString());
    }

    public Player[] getPlayers() {
        return players;
    }

    public SimpleStringProperty getMapTypeProperty() {
        return this.mapTypeProperty;
    }

    public SimpleStringProperty getDifficultyProperty() {
        return this.difficultyProperty;
    }
    public SimpleIntegerProperty getRoundNumberProperty() {
        return this.roundNumberProperty;
    }
    public SimpleIntegerProperty getGameNumberProperty() {
        return this.gameNumberProperty;
    }
    public SimpleStringProperty getDateProperty() {
        return this.dateProperty;
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
