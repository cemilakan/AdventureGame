package classes;

public class Armor {
    private String name;
    private int id;
    private int type;
    private int defence;
    private int price;

    public Armor(String name, int id, int type, int defence, int price) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.defence = defence;
        this.price = price;
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

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
