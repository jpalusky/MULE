package model;

/**
 * Enum for the available color settings.
 */
public enum Color {
    BLUE("Blue", javafx.scene.paint.Color.BLUE),
    GREEN("Green", javafx.scene.paint.Color.GREEN),
    RED("Red", javafx.scene.paint.Color.RED),
    YELLOW("Yellow", javafx.scene.paint.Color.YELLOW);

    private String label;
    private javafx.scene.paint.Color color;

    Color(String label, javafx.scene.paint.Color color) {
        this.label = label;
        this.color = color;
    }

    @Override
    public String toString() {
        return label;
    }
}

