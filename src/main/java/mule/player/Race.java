package mule.player;

/**
 * Enum for the available races in the game.
 */
public enum Race {
    HUMAN("Human", 600),
    FLAPPER("Flapper", 1600),
    BONZOID("Bonzoid", 1000),
    UGAITE("Ugaite", 1000),
    BUZZITE("Buzzite", 1000);

    private final String label;
    private final int startingMoney;

    Race(String label, int startingMoney) {
        this.label = label;
        this.startingMoney = startingMoney;
    }

    public int getStartingMoney() {
        return startingMoney;
    }

    @Override
    public String toString() {
        return label;
    }
}
