package mule;

/**
 * Model for available difficulty settings.
 */
public enum Difficulty {
    BEGINNER("Beginner", 8, 4, 0, 16, 16, 0, 25),
    STANDARD("Standard", 4, 2, 0, 8, 8, 8, 14),
    TOURNAMENT("Tournament", 4, 2, 0, 8, 8, 8, 14);

    private final String label;
    private final int food, energy, ore;
    private final int storeFood, storeEnergy, storeOre, storeMule;

    Difficulty(String label, int food, int energy, int ore,
               int storeFood, int storeEnergy, int storeOre, int storeMule) {
        this.label = label;
        this.food = food;
        this.energy = energy;
        this.ore = ore;
        this.storeFood = storeFood;
        this.storeEnergy = storeEnergy;
        this.storeOre = storeOre;
        this.storeMule = storeMule;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getStartingFood() {
        return food;
    }

    public int getStartingEnergy() {
        return energy;
    }

    public int getStartingOre() {
        return ore;
    }

    public int getStoreFood() {
        return this.storeFood;
    }

    public int getStoreEnergy() {
        return this.storeEnergy;
    }

    public int getStoreOre() {
        return this.storeOre;
    }

    public int getStoreMule() {
        return this.storeMule;
    }
}
