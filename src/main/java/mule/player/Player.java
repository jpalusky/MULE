package mule.player;

import com.airhacks.afterburner.injection.Injector;
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

    private final String name;
    private final Color color;
    private final Race race;

    private Point location;
    private int money;

    private final Set<Tile> properties;

    public Player(String name, Color color, Race race) {
        this.name = name;
        this.color = color;
        this.race = race;
        money = race.getStartingMoney();
        location = new Point();
        properties = new HashSet<>();
        Injector.injectMembers(getClass(), this);
    }

    public int getMoney() {
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
        money = Math.max(0, money - amount);
    }

    /**
     * The player buys the specified property.
     *
     * @param property the property to buy.
     * @return whether or not the property was bought.
     */
    public boolean buyProperty(Tile property) {
        if (property.isOwned()) return false;
        if (property.getCost() > money) return false;

        property.setOwner(this);
        properties.add(property);

        if (gameState.getRound() >= 2)  // Properties are free until after 2nd round.
            debitMoney(property.getCost());

        return true;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
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
