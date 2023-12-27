package classes;

import abstracts.Character;
import abstracts.HomeLand;

public class Shelter extends HomeLand {
    public Shelter(Player player) {
        super("Sığınak");
        setPlayer(player);
    }

    public void enter(Character character) {
        getPlayer().getInventory().setFood(getPlayer().getInventory().getFood() - 10);
        getPlayer().getInventory().setWater(getPlayer().getInventory().getWater() - 10);
        getPlayer().setCharacter(character);
        System.out.println("Can, Yemek ve Sağlık değerleri sıfırlandı.");
    }
}
