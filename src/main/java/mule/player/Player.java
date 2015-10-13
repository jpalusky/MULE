package mule.player;

import com.airhacks.afterburner.injection.Injector;
import javafx.beans.property.*;
import mule.Difficulty;
import mule.world.map.Point;
import mule.world.map.tile.Tile;

import java.util.HashSet;
import java.util.Set;

/**
 * This is the model for a Player.
 */
public class Player {
    private final StringProperty name;
    private final Color color;
    private final Race race;

    private final ObjectProperty<Point> mapLocation;
    private final ObjectProperty<Point> townLocation;

    private final IntegerProperty money;
    private final IntegerProperty food;
    private final IntegerProperty energy;
    private final IntegerProperty ore;

    private boolean inTown;

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
        energy = new SimpleIntegerProperty();
        ore = new SimpleIntegerProperty();
        mapLocation = new SimpleObjectProperty<>(new Point(0, 0));
        townLocation = new SimpleObjectProperty<>(new Point(0, 0));
        properties = new HashSet<>();
        Injector.injectMembers(getClass(), this);
    }

    /**
     * Configure the default resources of the player based on the difficulty.
     *
     * @param d the game difficulty.
     */
    public void setDefaultResources(Difficulty d) {
        food.set(d.getStartingFood());
        energy.set(d.getStartingEnergy());
        ore.set(d.getStartingOre());
    }

    public int getMoney() {
        return money.get();
    }

    public IntegerProperty getMoneyProp() {
        return money;
    }

    public IntegerProperty getFoodProp() {
        return food;
    }

    public IntegerProperty getEnergyProp() {
        return energy;
    }

    public IntegerProperty getOreProp() {
        return ore;
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

    public void addMoney(int amount) {
        debitMoney(-amount);
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
        return getMoney() + 500*properties.size() + getEnergy()
                + getFood() + getOre();
    }

    public int getFood() {
        return food.getValue();
    }

    public int getOre() {
        return ore.get();
    }

    public int getEnergy() {
        return energy.get();
    }

    public ObjectProperty<Point> getMapLocationProp() {
        return mapLocation;
    }

    public Point getMapLocation() {
        return mapLocation.get();
    }

    public void enterTown() {
        inTown = true;
    }

    public void exitTown() {
        inTown = false;
    }

    public boolean isInTown() {
        return inTown;
    }

    public void moveUp() {
        if (!inTown) {
            Point p = mapLocation.get();
            mapLocation.set(new Point(p.x, Math.max(0, p.y - 1)));
        }
    }

    public void moveDown() {
        if (!inTown) {
            Point p = mapLocation.get();
            mapLocation.set(new Point(p.x, Math.min(4, p.y + 1)));
        }
    }

    public void moveLeft() {
        if (!inTown) {
            Point p = mapLocation.get();
            mapLocation.set(new Point(Math.max(0, p.x - 1), p.y));
        }
    }

    public void moveRight() {
        if (!inTown) {
            Point p = mapLocation.get();
            mapLocation.set(new Point(Math.min(8, p.x + 1), p.y));
        }
    }

    @Override
    public String toString() {
        return name + " " + color + " " + race;
    }
}
