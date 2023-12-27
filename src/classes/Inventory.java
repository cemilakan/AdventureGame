package classes;

import java.util.Arrays;

public class Inventory {
    private Weapon weapon;
    private int water = 100;
    private int food = 100;
    private Armor[] armors;
    private int money = 0;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor[] getArmors() {
        return armors == null || armors.length < 1 ? new Armor[0] : armors;
    }

    public void setArmors(Armor[] armors) {
        this.armors = armors;
    }
    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = Math.min(Math.max(water, 0), 100);
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = Math.min(Math.max(food, 0), 100);
    }

    public int totalDefence() {
        Armor[] currentArmors = getArmors();
        return currentArmors == null || currentArmors.length < 1 ? 0 : Arrays.stream(currentArmors).mapToInt(Armor::getDefence).sum();
    }
}
