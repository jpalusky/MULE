package mule;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;
import mule.player.Player;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Manager for player turns and rounds.
 */
public class TurnManager extends AnimationTimer {
    @Inject private GameState gameState;

    private long startTime;
    private IntegerProperty roundNumber;
    private DoubleProperty timeLeft;
    private ObjectProperty<Player> currentPlayer;

    private Queue<Player> players;

    public TurnManager() {
        roundNumber = new SimpleIntegerProperty();
        timeLeft = new SimpleDoubleProperty();
        currentPlayer = new SimpleObjectProperty<>();
        players = new PriorityQueue<>(4, (a, b) -> a.calcScore() - b.calcScore());
    }

    @Override
    public void handle(long now) {  // now is in nanoseconds
        // Normalize startTime.
        if (startTime == -1) startTime = now;

        if (players.isEmpty()) {
            roundNumber.set(roundNumber.get() + 1);
            loadQueue();
        }

        // Load the current player the first time.
        if (currentPlayer.get() == null) {
            currentPlayer.set(players.remove());
            System.out.println(currentPlayer.get());
        }
    }

    private int calcFoodRequirements() {
        if (roundNumber.get() < 5) return 3;
        if (roundNumber.get() < 9) return 4;
        return 5;
    }

    private void loadQueue() {
        players.addAll(Arrays.asList(gameState.getPlayers()));
    }

    public IntegerProperty getRoundNumberProp() {
        return roundNumber;
    }

    public ObjectProperty<Player> getCurrentPlayerProp() {
        return currentPlayer;
    }

    @Override
    public void start() {
        // Normalize startTime.
        startTime = -1;
        super.start();
    }
}
