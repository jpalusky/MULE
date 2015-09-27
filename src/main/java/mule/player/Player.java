package mule.player;

import com.airhacks.afterburner.injection.Injector;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mule.GameState;
import mule.world.map.Point;
import mule.world.map.tile.Tile;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the model for a Player.
 */
public class Player {
    @Inject private GameState gameState;

    private final StringProperty name;
    private final Color color;
    private final Race race;

    private Point location;
    private IntegerProperty money;

    private final Set<Tile> properties;

    // Empty constructor for the injector.
    public Player() {
        this(null, Color.BLUE, Race.HUMAN);
    }

    public Player(String name, Color color, Race race) {
        this.name = new SimpleStringProperty(name);
        this.color = color;
        this.race = race;
        money = new SimpleIntegerProperty(race.getStartingMoney());
        location = new Point();
        properties = new HashSet<>();
        Injector.injectMembers(getClass(), this);
    }

    public int getMoney() {
        return money.get();
    }

    public IntegerProperty getMoneyProp() {
        return money;
    }

    /**
     * Remove a specified amount of money from the player's funds.
     *
     * This number can not be negative and debiting an amount
     * higher than the player's current funds will make the money 0.
     *
     * @param amount the amount of money to debit.
     */
    public void debitMoney(int amount) {
        money.set(Math.max(0, getMoney() - amount));
    }

    public Set<Tile> getProperties() {
        return properties;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProp() {
        return name;
    }

    public void moveUp() {
        location.y = Math.max(0, location.y - 1);
    }

    public void moveDown() {
        location.y = Math.min(4, location.y + 1);
    }

    public void moveLeft() {
        location.x = Math.max(0, location.x - 1);
    }

    public void moveRight() {
        location.x = Math.min(8, location.x + 1);
    }

    @Override
    public String toString() {
        return name + " " + color + " " + race;
    }
}
