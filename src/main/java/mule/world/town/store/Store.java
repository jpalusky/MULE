package mule.world.town.store;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import mule.Difficulty;
import mule.MuleType;
import mule.player.Player;

public class Store {
    private static final int FOOD_COST = 30,
                             ENERGY_COST = 25,
                             ORE_COST = 50,
                             MULE_COST = 100;

    private boolean configured;
    private IntegerProperty food, energy, ore, mule;

    public Store() {
        food = new SimpleIntegerProperty(16);
        energy = new SimpleIntegerProperty(16);
        ore = new SimpleIntegerProperty(0);
        mule = new SimpleIntegerProperty(25);
    }

    public void configureDefaults(Difficulty d) {
        if (configured) return;
        food = new SimpleIntegerProperty(d.getStoreFood());
        energy = new SimpleIntegerProperty(d.getStoreEnergy());
        ore = new SimpleIntegerProperty(d.getStoreOre());
        mule = new SimpleIntegerProperty(d.getStoreMule());
        configured = true;
    }

    public IntegerProperty getFoodProp() {
        return food;
    }

    public IntegerProperty getEnergyProp() {
        return energy;
    }

    public IntegerProperty getOreProp() {
        return ore;
    }

    public IntegerProperty getMuleProp() {
        return mule;
    }

    public void sellFood(Player p) {
        if (food.get() > 0 && p.buyFood(FOOD_COST, 1)) {
            food.set(food.get() - 1);
        }
    }

    public void buyFood(Player p) {
        if (p.buyFood(FOOD_COST, -1)) {
            food.set(food.get() + 1);
        }
    }

    public void sellEnergy(Player p) {
        if (energy.get() > 0 && p.buyEnergy(ENERGY_COST, 1)) {
            energy.set(energy.get() - 1);
        }
    }

    public void buyEnergy(Player p) {
        if (p.buyEnergy(ENERGY_COST, -1)) {
            energy.set(energy.get() + 1);
        }
    }

    public void sellOre(Player p) {
        if (ore.get() > 0 && p.buyOre(ORE_COST, 1)) {
            ore.set(ore.get() - 1);
        }
    }

    public void buyOre(Player p) {
        if (p.buyOre(ORE_COST, -1)) {
            ore.set(ore.get() + 1);
        }
    }

    public void sellMule(Player p, MuleType mt) {
        if (mule.get() > 0 && p.buyMule(MULE_COST, mt)) {
            mule.set(mule.get() - 1);
        }
    }

    public void buyMule(Player p) {
        if (p.sellMule(MULE_COST)) {
            mule.set(mule.get() + 1);
        }
    }
}
