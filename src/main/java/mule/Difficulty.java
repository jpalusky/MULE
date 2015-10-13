package mule;

/**
 * Model for available difficulty settings.
 */
public enum Difficulty {
    BEGINNER("Beginner", 8, 4, 0),
    STANDARD("Standard", 4, 2, 0),
    TOURNAMENT("Tournament", 4, 2, 0);

    private final String label;
    private final int food, energy, ore;

    Difficulty(String label, int food, int energy, int ore) {
        this.label = label;
        this.food = food;
        this.energy = energy;
        this.ore = ore;
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
}
