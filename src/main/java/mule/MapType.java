package mule;

/**
 * Model for the enumerate map types.
 */
public enum MapType {
    STANDARD("Standard"),
    RANDOM("Random");

    private String label;

    MapType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
