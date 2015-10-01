package mule;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;
import javafx.scene.input.KeyCode;
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
    @Inject private KeyHandler keyHandler;

    private long startTime;
    private long endTime;

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
        // Normalize startTime on first start.
        if (startTime == -1) startTime = now;

        // Reload the player queue at start of new round.
        if (players.isEmpty()) {
            roundNumber.set(roundNumber.get() + 1);
            loadQueue();
        }

        // Change players when their turn ends.
        if (now > endTime) {
            currentPlayer.set(players.remove());
            startTime = now;
            endTime = startTime + calcTurnTime() * ((long) 1e9);
        }
        timeLeft.set((endTime - now) / 1e9);
    }

    /**
     * Calculate the food requirements for the current round.
     *
     * @return the food required.
     */
    private int calcFoodRequirements() {
        if (roundNumber.get() < 5) return 3;
        if (roundNumber.get() < 9) return 4;
        return 5;
    }

    /**
     * Calculate the amount of time the player has for the current turn.
     *
     * @return the amount of time in seconds.
     */
    private int calcTurnTime() {
        if (getCurrentPlayer().getFood() <= 0) return 5;
        if (getCurrentPlayer().getFood() < calcFoodRequirements()) return 30;
        return 50;
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

    public Player getCurrentPlayer() {
        return currentPlayer.get();
    }

    public DoubleProperty getTimeLeftProp() {
        return timeLeft;
    }

    @Override
    public void start() {
        // Normalize startTime.
        startTime = -1;
        bindMovementKeys();
        super.start();
    }

    private void bindMovementKeys() {
        keyHandler.bind(KeyCode.RIGHT, e -> getCurrentPlayer().moveRight());
        keyHandler.bind(KeyCode.LEFT, e -> getCurrentPlayer().moveLeft());
        keyHandler.bind(KeyCode.DOWN, e -> getCurrentPlayer().moveDown());
        keyHandler.bind(KeyCode.UP, e -> getCurrentPlayer().moveUp());
    }
}
