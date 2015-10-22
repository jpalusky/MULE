package mule;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import mule.mainscreen.MainScreen;
import mule.player.Player;
import mule.world.map.tile.Tile;

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
    @Inject private MainScreen mainScreen;

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

            doProduction();

            if (players.size() != gameState.getPlayers().length - 1) {
                RandomEvent event = RandomEvent.chooseEvent();
                if (event != null) {
                    //Show dialog box
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Random Event");
                    alert.setHeaderText(null);
                    alert.setContentText(event.getDescription());

                    alert.show();
                    event.runEvent(getCurrentPlayer(), getRoundNumber());
                }
            }

            if (currentPlayer.get().isInTown()) {
                mainScreen.showTown();
            }
            else {
                mainScreen.showMap();
            }

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

    public double getTimeLeft() {
        return timeLeft.get();
    }

    public int getRoundNumber() {
        return roundNumber.get();
    }

    public void endTurn() {
        endTime = -Long.MIN_VALUE;
    }

    private void doProduction() {
        Player player = currentPlayer.get();
        if (player == null) return;
        player.getProperties().stream().filter(Tile::hasMule).forEach(Tile::produce);
    }

    @Override
    public void start() {
        // Normalize startTime.
        startTime = -1;
        super.start();
    }
}
