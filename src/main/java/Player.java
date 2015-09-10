package sample;

/**
 * Created by David on 9/10/15.
 */
public class Player {
    String name;
    PlayerColor color;
    Race race;
    public Player() {
        name = "";
    }
    public void setColor(PlayerColor color) {
        this.color = color;
    }
    public void setRace(Race race) {
        this.race = race;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public PlayerColor getColor() {
        return this.color;
    }
    public Race getRace() {
        return this.race;
    }
}
