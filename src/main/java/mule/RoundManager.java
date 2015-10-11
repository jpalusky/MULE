package mule;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javax.inject.Inject;

/**
 * Created by David on 10/7/2015.
 */
public class RoundManager {
    private SimpleBooleanProperty isWaitingProp;
    private TurnManager turnManager;
    private IntegerProperty roundNumber;

    public RoundManager() {
        roundNumber = new SimpleIntegerProperty(0);
        isWaitingProp = new SimpleBooleanProperty(true);
        turnManager = new TurnManager();
    }

    public void start() {
        turnManager.start();
    }

    public SimpleBooleanProperty getIsWaitingProp() {
        return isWaitingProp;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public IntegerProperty getRoundNumberProp() {
        return roundNumber;
    }
    public int getRoundNumber() {
        return roundNumber.get();
    }

}
