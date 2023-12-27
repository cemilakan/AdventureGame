package abstracts;

import classes.characters.Archer;
import classes.characters.Knight;
import classes.characters.Samurai;

public abstract class Character {
    private String name;
    private int id;
    private int damage;
    private int health;
    private int money;

    public Character(String name, int id, int damage, int health, int money) {
        this.name = name;
        this.id = id;
        this.damage = damage;
        this.health = health;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}