package mule;

/**
 * Model for available difficulty settings.
 */
public enum Difficulty {
    BEGINNER("Beginner"),
    STANDARD("Standard"),
    TOURNAMENT("Tournament");

    private String label;

    Difficulty(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
