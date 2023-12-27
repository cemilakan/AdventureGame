package classes;

import abstracts.Character;

import java.util.Arrays;

public class Player {
    private String name;
    private Character character;
    private Inventory inventory = new Inventory();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int playerDefence() {
        return getInventory().getArmors() == null || getInventory().getArmors().length < 1 ? 0 : Arrays.stream(getInventory().getArmors()).mapToInt(Armor::getDefence).sum();
    }

    public int playerDamage() {
        return inventory.getWeapon() != null ? getInventory().getWeapon().getDamage() + getCharacter().getDamage() : 0;
    }

}
