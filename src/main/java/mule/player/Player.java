package mule.player;

/**
 * This is the model for a Player.
 */
public class Player {
    private final String name;
    private final Color color;
    private final Race race;

    /** The x and y coordinates of the player. */
    private int x, y;

    private int money;

    public Player(String name, Color color, Race race) {
        this.name = name;
        this.color = color;
        this.race = race;
        money = race.getStartingMoney();
    }

    public int getMoney() {
        return money;
    }

    public void debitMoney(int amount) {
        money -= amount;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveUp() {
        y = Math.max(0, y - 1);
    }

    public void moveDown() {
        y = Math.min(4, y + 1);
    }

    public void moveLeft() {
        x = Math.max(0, x - 1);
    }

    public void moveRight() {
        x = Math.min(8, x + 1);
    }

    @Override
    public String toString() {
        return name + " " + color + " " + race;
    }
}
