package classes;

import abstracts.HomeLand;

import java.util.Arrays;
import java.util.Scanner;

public class Armory extends HomeLand {

    private Weapon[] weapons;
    private Armor[] armors;

    {
        Armor helmet = new Armor("Kask", 1,1,1,15);
        Armor armAndKneePad = new Armor("Kolluk ve Dizlik", 2,2,3,25);
        Armor vest = new Armor("Çelik Yelek", 3,3,5,40);
        this.armors = new Armor[]{helmet, armAndKneePad, vest};

        Weapon pistol = new Weapon("Tabanca", 1,2,25);
        Weapon sword = new Weapon("Kılıç", 2,3,10);
        Weapon fusil = new Weapon("Tüfek", 3,7,45);
        this.weapons = new Weapon[]{pistol, sword, fusil};
    }

    public Armory(Player player) {
        super("Cephanelik");
        setPlayer(player);
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;
    }

    public Armor[] getArmors() {
        return armors;
    }

    public void setArmors(Armor[] armors) {
        this.armors = armors;
    }

    public void welcomeArmory() {
        System.out.println("Cephaneliğe Hoş Geldiniz\n Hangi İşlemi Yapmak istiyorsunuz?\n\t 0- Cephanlikten Çıkış \n\t 1- Zırh Satın Alma \n\t 2- Silah Satın Alma");
        Scanner scanner = new Scanner(System.in);
        int action = scanner.nextInt();
        if (action == 1) {
            pickArmor();
        } else if (action == 2) {
            pickWeapon();
        }else {
            System.out.println("Geçersiz İşlem");
        }
    }

    private Weapon[] getAvailableWeapons(int money) {
        return Arrays.stream(getWeapons()).filter(w -> w.getPrice() <= money).toList().toArray(new Weapon[0]);
    }

    private void printAvailableWeapons(int money) {
        for(Weapon w : getAvailableWeapons(money))
            System.out.println(w.getId() + "- " + w.getName()
                    + ";\n\t" + "Hasar: " + w.getDamage()
                    + "\n\t" + "Ücret: " + w.getPrice());
    }

    private Armor[] getAvailableArmors(int money) {
        return Arrays.stream(getArmors()).filter(w -> w.getPrice() <= money).toList().toArray(new Armor[0]);
    }

    private void printAvailableArmors(int money) {
        for(Armor a : getAvailableArmors(money))
            System.out.println(a.getId() + "- " + a.getName()
                    + ";\n\t" + "Zırh: " + a.getDefence()
                    + "\n\t" + "Ücret: " + a.getPrice());
    }

    public void pickArmor() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Armor[] availableArmors = getAvailableArmors(getPlayer().getInventory().getMoney());
            if (availableArmors.length == 0) {
                System.out.println("Yeterince paranız olmadığı için zırh alamazsınız");
                break;
            }else {
                System.out.println("Satın alabileceğiniz zırhlardan birini seçiniz.\n Almak için seçim yapınız. \n İstemiyorsanız 0'ı giriniz");
                printAvailableArmors(getPlayer().getInventory().getMoney());
                int selectedArmorId = scanner.nextInt();
                if (selectedArmorId == 0) break;
                Armor armor = Arrays.stream(availableArmors).toList().stream().filter(c -> c.getId() == selectedArmorId).findFirst().orElse(null);
                int armorCount = getPlayer().getInventory().getArmors() == null || getPlayer().getInventory().getArmors().length < 1  ? 0 : getPlayer().getInventory().getArmors().length;
                Armor[] armors = new Armor[armorCount + 1];
                Armor[] current = getPlayer().getInventory().getArmors();
                //sahip olduğu zırhlar listelenmeyecek
                if (armorCount > 0)
                    System.arraycopy(current, 0, armors, 0, current.length);
                if(armor != null) {
                    armors[armorCount] = armor;
                    System.out.println("Zırh Başarıyla Kuşanıldı.");
                    getPlayer().getInventory().setArmors(armors);
                    break;
                }else {
                    System.out.println("Aradığınız zırh bulunamadı tekrar deneyiniz");
                }
            }

        }
    }

    private void pickWeapon() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Weapon[] availableWeapons = getAvailableWeapons(getPlayer().getInventory().getMoney());
            if (availableWeapons.length == 0) {
                System.out.println("Yeterince paranız olmadığı için silah alamazsınız");
                break;
            }else {
                System.out.println("Satın alabileceğiniz silahlardan birini seçiniz.\n Almak için seçim yapınız \n İstemiyorsanız 0'ı giriniz");
                printAvailableWeapons(getPlayer().getInventory().getMoney());
                int selectedWeaponId = scanner.nextInt();
                if (selectedWeaponId == 0) break;
                Weapon weapon = Arrays.stream(availableWeapons).toList().stream().filter(c -> c.getId() == selectedWeaponId).findFirst().orElse(null);
                if(weapon != null) {
                    System.out.println("Silah Başarıyla Seçilmiştir.");
                    getPlayer().getInventory().setMoney(getPlayer().getInventory().getMoney() - weapon.getPrice());
                    getPlayer().getInventory().setWeapon(weapon);
                    break;
                }else {
                    System.out.println("Aradığınız silah bulunamadı tekrar deneyiniz");
                }
            }
        }
    }


}
