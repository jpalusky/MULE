package mule.player;

import com.airhacks.afterburner.injection.Injector;
import javafx.beans.property.*;
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

    private final ObjectProperty<Point> location;

    private IntegerProperty money;
    private IntegerProperty food;

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
        food = new SimpleIntegerProperty();
        location = new SimpleObjectProperty<>(new Point(0, 0));
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

    public void addProperty(Tile property) {
        properties.add(property);
    }

    public boolean owns(Tile property) {
        return properties.contains(property);
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

    /**
     * Calculate the player's score based on the following criteria:
     *
     * - 1 point per dollar of money.
     * - 500 points for each plot of land owned.
     * - 1 point per dollar value of goods (energy, food, ore).
     *
     * @return the player's score.
     */
    public int calcScore() {
        // TODO: Include other resources in score computation.
        return getMoney() + 500*properties.size();
    }

    public int getFood() {
        return food.getValue();
    }

    public ObjectProperty<Point> getLocationProp() {
        return location;
    }

    public Point getLocation() {
        return location.get();
    }

    public void moveUp() {
        Point p = location.get();
        location.set(new Point(p.x, Math.max(0, p.y - 1)));
    }

    public void moveDown() {
        Point p = location.get();
        location.set(new Point(p.x, Math.min(4, p.y + 1)));
    }

    public void moveLeft() {
        Point p = location.get();
        location.set(new Point(Math.max(0, p.x - 1), p.y));
    }

    public void moveRight() {
        Point p = location.get();
        location.set(new Point(Math.min(8, p.x + 1), p.y));
    }

    @Override
    public String toString() {
        return name + " " + color + " " + race;
    }
}
