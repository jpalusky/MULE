package model;

/**
 * Model for available difficulty settings.
 */
public enum Difficulty {
    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard");

    private String label;

    Difficulty(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
