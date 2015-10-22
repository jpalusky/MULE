package mule;

import mule.player.Player;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

public enum RandomEvent {
    ALUMNI_PACKAGE((Player player) -> (Integer m) -> {
        //Add 3 food and 2 energy
        player.getFoodProp().add(3);
        player.getEnergyProp().add(2);
    }, "YOU JUST RECEIVED A PACKAGE FROM THE GT ALUMNI CONTAINING 3 FOOD AND 2 ENERGY UNITS."),
    WANDERING_STUDENT((Player player) -> (Integer m) -> {
        //Add 2 ore
        player.getOreProp().add(2);
    }, "A WANDERING TECH STUDENT REPAID YOUR HOSPITALITY BY LEAVING TWO BARS OF ORE."),
    MUSEUM_PURCHASE((Player player) -> (Integer m) -> {
        //Add 8 * m (from current round)
        player.addMoney(8*m);
    }, "THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $ 8*m."),
    DEAD_RAT((Player player) -> (Integer m) -> {
        //Get 2*m money
        player.addMoney(2*m);
    }, "YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $2*m."),
    FLYING_CATBUGS((Player player) -> (Integer m) -> {
        //Lose 4*m money
        player.debitMoney(4*m);
    }, "FLYING CAT-BUGS ATE THE ROOF OFF YOUR HOUSE. REPAIRS COST $4*m."),
    UGA_STUDENT((Player player) -> (Integer m) -> {
        player.getFoodProp().set(player.getFood() / 2);
    }, "MISCHIEVOUS UGA STUDENTS BROKE INTO YOUR STORAGE SHED AND STOLE HALF YOUR FOOD."),
    SPACE_GYPSY((Player player) -> (Integer m) -> {
        player.debitMoney(6*m);
    }, "YOUR SPACE GYPSY INLAWS MADE A MESS OF THE TOWN. IT COST YOU $6*m TO CLEAN IT UP.");

    private final Function<Player, Consumer<Integer>> event;

    private String description;

    private static final int[] roundFactor = {
        25, 25, 25, 50, 50, 50, 50, 75, 75, 75, 75, 100
    };
    private static Random random = new Random();

    RandomEvent(Function<Player, Consumer<Integer>> event, String description) {
        this.event = event;
        this.description = description;
    }

    public void runEvent(Player player, int round) {
        int m = (round < 13 && round > 0) ? roundFactor[round - 1] : 0;
        try {
            event.apply(player).accept(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return this.description;
    }

    public static RandomEvent chooseEvent() {
        if (random.nextInt(100) < 1000) {
            RandomEvent[] events = RandomEvent.values();
            int eventIndex = random.nextInt(events.length);
            return events[0];
        } else {
            return null;
        }
    }
}
