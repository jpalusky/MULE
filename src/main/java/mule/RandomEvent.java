package mule;

import mule.player.Player;

import java.util.concurrent.Callable;

public enum RandomEvent {
    ALUMNI_PACKAGE((Player player) -> {
        //Add 3 food and 2 energy
        player.getFoodProp().add(3);
        player.getEnergyProp().add(2);
    }),
    WANDERING_STUDENT((Player player) -> {
        //Add 2 ore
        player.getOreProp().add(2);
    }),
    MUSEUM_PURCHASE((Player player) -> {
        //Add 8 * m (from current round)

    })
    ;

    Player player;
    PlayerCallable event;

    RandomEvent(PlayerCallable event) {
        this.event = event;
        this.player = null;
    }

    public void runEvent(Player player) {
        try {
            event.call(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
