package mule;

import javafx.util.StringConverter;

public enum MuleType {
    FOOD(25),
    ENERGY(50),
    ORE(75),
    NONE(0);

    private int cost;

    MuleType(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public static class MuleStringConverter extends StringConverter<MuleType> {
        @Override
        public String toString(MuleType object) {
            return object.toString();
        }

        @Override
        public MuleType fromString(String string) {
            if (string.equals("FOOD")) return FOOD;
            if (string.equals("ENERGY")) return ENERGY;
            if (string.equals("ORE")) return ORE;
            return NONE;
        }
    }
}
