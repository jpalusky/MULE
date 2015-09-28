package mule.world.map;

/**
 * Model for the enumerated map types.
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
