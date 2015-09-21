package mule.player;

/**
 * Enum for the available color settings.
 *
 * This is a light wrapper for javafx.scene.paint.Color
 * with a limited selection of available colors.
 */
public enum Color {
    BLUE("Blue", javafx.scene.paint.Color.BLUE),
    GREEN("Green", javafx.scene.paint.Color.GREEN),
    RED("Red", javafx.scene.paint.Color.RED),
    PINK("Pink", javafx.scene.paint.Color.PINK),
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

