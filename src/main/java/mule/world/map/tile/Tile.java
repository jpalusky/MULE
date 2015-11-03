package mule.world.map.tile;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import mule.MuleType;
import mule.player.Player;
import mule.world.map.Point;

/**
 * A tile on the map.
 */
public class Tile {
    private final ObjectProperty<TileType> type;
    private final ObjectProperty<Player> owner;
    private final ObjectProperty<Player> player;
    private final ObjectProperty<MuleType> mule;

    private final Point location;

    // This is only here because the injector uses reflection
    // to instantiate the class with the empty constructor.
    public Tile() {
        this(TileType.PLAIN, 0, 0);
    }

    public Tile(TileType type, int x, int y) {
        this.type = new SimpleObjectProperty<>(type);
        owner = new SimpleObjectProperty<>();
        player = new SimpleObjectProperty<>();
        mule = new SimpleObjectProperty<>(MuleType.NONE);
        location = new Point(x, y);
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

    public ObjectProperty<MuleType> getMuleProp() {
        return mule;
    }

    public void addMule(MuleType mule) {
        this.mule.set(mule);
    }

    public boolean hasMule() {
        return mule.get() != MuleType.NONE;
    }

    public boolean produce() {
        Player owner = this.owner.get();
        if (owner == null) return false;
        if (owner.getEnergy() < 1) return false;
        if (!hasMule()) return false;

        owner.addEnergy(-1);
        TileType t = getTileType();
        switch (mule.get()) {
            case FOOD:
                owner.addFood(t.getFoodProduction());
                break;
            case ORE:
                owner.addOre(t.getOreProduction());
                break;
            default:
                owner.addEnergy(t.getEnergyProduction());
        }

        return true;
    }

    public Point getLocation() {
        return location;
    }
}
