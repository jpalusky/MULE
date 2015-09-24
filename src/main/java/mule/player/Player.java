package mule.player;

/**
 * This is the model for a Player.
 */
public class Player {
    private final String name;
    private final Color color;
    private final Race race;

    private int money;

    public Player(String name, Color color, Race race) {
        this.name = name;
        this.color = color;
        this.race = race;
        money = race.getStartingMoney();
    }
}
