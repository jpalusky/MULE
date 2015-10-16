package mule.world.town;

/**
 * Created by evanyui on 9/22/15.
 */
public class Building {
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private String name;

    public Building(String name, double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.name = name;
    }

    public double getxMin() {
        return this.xMin;
    }

    public double getxMax() {
        return this.xMax;
    }

    public double getyMin() {
        return this.yMin;
    }

    public double getyMax() {
        return this.yMax;
    }

    public boolean in(double x, double y) {
        return x > xMin && x < xMax && y > yMin && y < yMax;
    }

    public String getName() {
        return this.name;
    }
}
