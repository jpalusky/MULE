package mule;

import mule.player.Player;

import java.util.function.Consumer;
import java.util.function.Function;

public enum RandomEvent {
    ALUMNI_PACKAGE((Player player) -> (Integer m) -> {
        //Add 3 food and 2 energy
        player.getFoodProp().add(3);
        player.getEnergyProp().add(2);
    }),
    WANDERING_STUDENT((Player player) -> (Integer m) -> {
        //Add 2 ore
        player.getOreProp().add(2);
    }),
    MUSEUM_PURCHASE((Player player) -> (Integer m) -> {
        //Add 8 * m (from current round)
        player.addMoney(8*m);
    }),
    DEAD_RAT((Player player) -> (Integer m) -> {
        //Get 2*m money
        player.addMoney(2*m);
    }),
    FLYING_CATBUGS((Player player) -> (Integer m) -> {
        //Lose 4*m money
        player.debitMoney(4*m);
    }),
    UGA_STUDENT((Player player) -> (Integer m) -> {
        player.getFoodProp().set(player.getFood() / 2);
    }),
    SPACE_GYPSY((Player player) -> (Integer m) -> {
        player.debitMoney(6*m);
    });

    private final Function<Player, Consumer<Integer>> event;

    private static final int[] roundFactor = {
        25, 25, 25, 50, 50, 50, 50, 75, 75, 75, 75, 100
    };

    RandomEvent(Function<Player, Consumer<Integer>> event) {
        this.event = event;
    }

    public void runEvent(Player player, int round) {
        int m = (round < 13 && round > 0) ? roundFactor[round - 1] : 0;
        try {
            event.apply(player).accept(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
