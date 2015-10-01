package mule.world.map.tile;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import mule.player.Player;

/**
 * A tile on the map.
 */
public class Tile {
    private final ObjectProperty<TileType> type;
    private final ObjectProperty<Player> owner;
    /** The players in the current tile. */
    private final ObjectProperty<Player> player;

    // This is only here because the injector uses reflection
    // to instantiate the class with the empty constructor.
    public Tile() {
        this(TileType.PLAIN);
    }

    public Tile(TileType type) {
        this.type = new SimpleObjectProperty<>(type);
        owner = new SimpleObjectProperty<>();
        player = new SimpleObjectProperty<>();
    }

    public TileType getTileType() {
        return type.get();
    }

    public Player getOwner() {
        return owner.get();
    }

    public void setOwner(Player owner) {
        this.owner.set(owner);
    }

    public boolean isOwned() {
        return getOwner() != null;
    }

    public int getCost() {
        return 300;
    }

    public ObjectProperty<Player> getOwnerProp() {
        return owner;
    }

    public ObjectProperty<Player> getPlayerProp() {
        return player;
    }

    public void addPlayer(Player player) {
        this.player.set(player);
    }

    public void removePlayer() {
        player.set(null);
    }
}
