package mule.world.map.tile;

/**
 * TileType is the model for a single tile on the map.
 */
public enum TileType {
    RIVER("River", 4, 2, 0, 0),
    PLAIN("Plain", 2, 3, 1, 5),
    MOUNTAIN1("One Mountain", 1, 1, 2, 5),
    MOUNTAIN2("Two Mountains", 1, 1, 3, 5),
    MOUNTAIN3("Three Mountains", 1, 1, 4, 5),
    TOWN("Town", 0, 0, 0, 0);

    private String label;
    private int food, energy, ore, crystite;

    TileType(String label, int food, int energy, int ore, int crystite) {
        this.label = label;
        this.food = food;
        this.energy = energy;
        this.ore = ore;
        this.crystite = crystite;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getFoodProduction() {
        return food;
    }

    public int getEnergyProduction() {
        return energy;
    }

    public int getOreProduction() {
        return ore;
    }

    public int getCrystiteProduction() {
        return (int) (Math.random() * crystite);
    }
}
