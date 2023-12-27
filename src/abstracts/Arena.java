package abstracts;

import classes.Armor;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public abstract class Arena extends Location{
    private Enemy enemy;

    public Arena(String name, Enemy enemy) {
        super(name);
        this.enemy = enemy;
    }


    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    /*
    * 0 iptal edildi
    * 1 kazanıldı
    * 2 kaybedildi
    * */
    public int clearLocation() {
        int status = 0;
        Enemy[] enemies = new Enemy[enemyCount()];
        System.out.println("Görünüşe göre "+ super.getName() +" bölgesinde yenmen gereken " + enemies.length + " adet " + this.enemy.getName() + " var.");
        System.out.println("Her " + this.enemy.getName() + " " + this.enemy.getDamage() + " hasara sahiptir");
        System.out.println("Savaşa başlamak istiyor musun?(e/h)");
        Scanner scanner = new Scanner(System.in);
        if(scanner.next().toLowerCase(Locale.ROOT).equals("h")){
            System.out.println("Saldırı iptal edildi...");
            return status;
        }
        System.out.println("Saldırı başlatılıyor");
        getPlayer().getInventory().setFood(getPlayer().getInventory().getFood() - 10);
        getPlayer().getInventory().setWater(getPlayer().getInventory().getWater() - 10);
        int totalDamage = getPlayer().getCharacter().getDamage() + (getPlayer().getInventory().getWeapon() != null ? getPlayer().getInventory().getWeapon().getDamage() : 0);
        int totalHealth = getPlayer().getCharacter().getHealth();
        int totalDefence = getPlayer().getInventory().getArmors() != null && getPlayer().getInventory().getArmors().length > 0 ? Arrays.stream(getPlayer().getInventory().getArmors()).mapToInt(Armor::getDefence).sum() : 0;
        loopEnemies:
        for (int i = 0; i < enemies.length; i++) {
            Enemy e = this.enemy;
            int damagePoint = e.getDamage() - (int) Math.round((double) totalDefence / 2);
            int enemyHealth = e.getHealth();
            loopThisEnemy:
            do {
                if (enemyHealth - totalDamage <= 0) {
                    System.out.println((i+1)+"." + this.enemy.getName() + " yenildi. Envanterine " + e.getMoney() + " ₺ eklendi.");
                    getPlayer().getInventory().setMoney(getPlayer().getInventory().getMoney() + e.getMoney());
                    break loopThisEnemy;
                } else if (getPlayer().getInventory().getFood() < 1) {
                    System.out.println("Yemeğiniz bitttiği için öldünüz \nOyun sona erdi.");
                    status = 2;
                    break loopEnemies;
                } else if (getPlayer().getInventory().getWater() < 1) {
                    System.out.println("Suzuz kaldığınız için öldünüz \nOyun sona erdi.");
                    status = 2;
                    break loopEnemies;
                } else {
                    enemyHealth -= totalDamage;
                    if (getPlayer().getCharacter().getHealth() - damagePoint < 0) {
                        getPlayer().getCharacter().setHealth(0);
                        System.out.println("Savaş Kaybedildi");
                        status = 2;
                        break loopEnemies;
                    }else {
                        getPlayer().getCharacter().setHealth(getPlayer().getCharacter().getHealth() - damagePoint);
                        System.out.println("Karakterinin Sağlık Durmu: " + getPlayer().getCharacter().getHealth());
                    }
                }
            }while (totalHealth > 0 || enemyHealth > 0);
        }
        return status;
    }

    private int enemyCount() {
        Random random = new Random();
        return random.nextInt(3) + 1;
    }

}
