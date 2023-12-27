import abstracts.Arena;
import classes.*;
import abstracts.Character;
import classes.arenas.Cave;
import classes.arenas.Jungle;
import classes.arenas.River;
import classes.characters.Archer;
import classes.characters.Knight;
import classes.characters.Samurai;
import classes.enemies.Bear;
import classes.enemies.Vampire;
import classes.enemies.Zombie;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Player player = new Player();
        Zombie zombie = new Zombie();
        Vampire vampire = new Vampire();
        Bear bear = new Bear();

        System.out.println("Oyuna Hoşgeldiniz");
        System.out.print("Öncelikle Bir Oyuncu Adı Giriniz:");
        player.setName(scanner.next());
        System.out.println("Şimdi kendinize listelenen karakterlerden birini seçiniz");
        //Karakter Seçimi
        pickCharacter(player);

        //Savaş arenalerı hazırlığı
        Jungle jungle = new Jungle(zombie);
        Cave cave = new Cave(bear);
        River river = new River(vampire);
        Arena[] arenas = {jungle,cave,river};

        Armory armory = new Armory(player);
        Shelter shelter = new Shelter(player);

        gameLoop:
        while (true) {
            System.out.println("""
                    Hangi işlemi yapmak istiyorsunuz?
                    \t1- Cephaneliğe Git
                    \t2- Sığınağa Git
                    \t3- Savaşa Git
                    \t4- Karakter Durumuna Git
                    \t5- Oyunu Bitir""");

            int action = scanner.nextInt();
            switch (action) {
                case 1:{
                    armory.welcomeArmory();
                    break;
                }
                case 2:{
                    Character defaultCharacter = Arrays.stream(getCharacters()).toList().stream().filter(c -> c.getId() == player.getCharacter().getId()).findFirst().orElse(null);
                    shelter.enter(defaultCharacter);
                    break;
                }
                case 3:{
                    Arena selectedArena = arenaList(arenas);
                    selectedArena.setPlayer(player);
                    selectedArena.clearLocation();
                    break;
                }
                case 4:{
                    System.out.println("Karakter Durumu;" +
                            "\n\tSağlık: " + player.getCharacter().getHealth() +
                            "\n\tSaldırı Gücü: " + (player.getInventory().getWeapon() == null ? "Yok" : (player.getInventory().getWeapon().getDamage() + player.getCharacter().getDamage())) +
                            "\n\tSu: " + player.getInventory().getWater() +
                            "\n\tPara: " + player.getInventory().getMoney() +
                            "\n\tYemek: " + player.getInventory().getFood() +
                            "\n\tZırh: " + player.playerDefence() +
                            "\n\tZırh Adedi: " + player.getInventory().getArmors().length
                    );
                    break;
                }
                case 5:{
                    System.out.println("Oyun bitmiştir.");
                    scanner.close();
                    break gameLoop;
                }
                default:{
                    System.out.println("Geçersiz İşlem");
                    break;
                }
            }
        }

    }

    private static void pickCharacter(Player player) {
        Character selectedCharacter;
        while (true) {
            printCharacters();
            int selectedCharacterId = scanner.nextInt();
            selectedCharacter = Arrays.stream(getCharacters()).toList().stream().filter(c -> c.getId() == selectedCharacterId).findFirst().orElse(null);
            if(selectedCharacter != null) {
                System.out.println("Karakter Başarıyla Seçilmiştir.");
                player.setCharacter(selectedCharacter);
                player.getInventory().setMoney(selectedCharacter.getMoney() + player.getInventory().getMoney());
                break;
            }else {
                System.out.println("Aradığınız karakter bulunamadı tekrar deneyiniz");
            }
        }

    }

    private static void printCharacters() {
        for(Character c : getCharacters())
            System.out.println(c.getId() + "- " + c.getName()
                    + "; " + "Hasar: " + c.getDamage()
                    + ", " + "Sağlık: " + c.getHealth()
                    + ", " + "Para: " + c.getMoney());
    }

    public static Arena arenaList(Arena[] arenas) {
        System.out.println("Savaşmak istediğiniz konumu seçiniz");
        int selected;
        while (true) {
            for(int i = 0; i < arenas.length; i++)
                System.out.println(
                        (i + 1) + "- Savaş konumu: " + arenas[i].getName() + "\n" + "Düşman: " + arenas[i].getEnemy().getName()
                );
            selected = scanner.nextInt() - 1;
            if(selected < 0 || selected > arenas.length ||arenas[selected] == null) System.out.println("Geçerli bir konum seçiniz");
            else break;
        }
        return arenas[selected];
    }
    /*
    public static void pickArmor(Player player) {
        while (true) {
            Armor[] availableArmors = getAvailableArmors(player.getInventory().getMoney());
            if (availableArmors.length == 0) {
                System.out.println("Yeterince paranız olmadığı için zırh alamazsınız");
                break;
            }else {
                System.out.println("Satın alabileceğiniz zırhlardan birini seçiniz.\n Almak için seçim yapınız. \n İstemiyorsanız 0'ı giriniz");
                printAvailableArmors(player.getInventory().getMoney());
                int selectedArmorId = scanner.nextInt();
                if (selectedArmorId == 0) break;
                //ilerleyen vakitte parası varsa farklı türden zırhlar alabilecek
                Armor armor = Arrays.stream(availableArmors).toList().stream().filter(c -> c.getId() == selectedArmorId).findFirst().orElse(null);
                Armor[] armors = {armor};
                if(armor != null) {
                    System.out.println("Zırh Başarıyla Kuşanıldı.");
                    player.getInventory().setArmors(armors);
                    break;
                }else {
                    System.out.println("Aradığınız zırh bulunamadı tekrar deneyiniz");
                }
            }

        }
    }
    private static void printAvailableArmors(int money) {
        for(Armor a : getAvailableArmors(money))
            System.out.println(a.getId() + "- " + a.getName()
                    + ";\n\t" + "Zırh: " + a.getDefence()
                    + "\n\t" + "Ücret: " + a.getPrice());
    }
    private static void pickWeapon(Player player) {
        while (true) {
            Weapon[] availableWeapons = getAvailableWeapons(player.getInventory().getMoney());
            if (availableWeapons.length == 0) {
                System.out.println("Yeterince paranız olmadığı için silah alamazsınız");
                break;
            }else {
                System.out.println("Satın alabileceğiniz silahlardan birini seçiniz.\n Almak için seçim yapınız \n İstemiyorsanız 0'ı giriniz");
                printAvailableWeapons(player.getInventory().getMoney());
                int selectedWeaponId = scanner.nextInt();
                if (selectedWeaponId == 0) break;
                Weapon weapon = Arrays.stream(availableWeapons).toList().stream().filter(c -> c.getId() == selectedWeaponId).findFirst().orElse(null);
                if(weapon != null) {
                    System.out.println("Silah Başarıyla Seçilmiştir.");
                    player.getInventory().setMoney(player.getInventory().getMoney() - weapon.getPrice());
                    player.getInventory().setWeapon(weapon);
                    break;
                }else {
                    System.out.println("Aradığınız silah bulunamadı tekrar deneyiniz");
                }
            }

        }
    }
    private static void printAvailableWeapons(int money) {
        for(Weapon w : getAvailableWeapons(money))
            System.out.println(w.getId() + "- " + w.getName()
                    + ";\n\t" + "Hasar: " + w.getDamage()
                    + "\n\t" + "Ücret: " + w.getPrice());
    }


    private static Armor[] getArmors() {
        Armor helmet = new Armor("Kask", 1,1,1,15);
        Armor armAndKneePad = new Armor("Kolluk ve Dizlik", 1,2,3,25);
        Armor vest = new Armor("Çelik Yelek", 1,3,5,40);
        return new Armor[]{helmet, armAndKneePad, vest};
    }
    private static Armor[] getAvailableArmors(int money) {
        return Arrays.stream(getArmors()).filter(w -> w.getPrice() <= money).toList().toArray(new Armor[0]);
    }
    private static Weapon[] getWeapons() {
        Weapon pistol = new Weapon("Tabanca", 1,2,25);
        Weapon sword = new Weapon("Kılıç", 2,3,10);
        Weapon fusil = new Weapon("Tüfek", 3,7,45);
        return new Weapon[]{pistol, sword, fusil};
    }
    private static Weapon[] getAvailableWeapons(int money) {
        return Arrays.stream(getWeapons()).filter(w -> w.getPrice() <= money).toList().toArray(new Weapon[0]);
    }
*/

    private static Character[] getCharacters() {
        return new Character[]{new Samurai(), new Archer(), new Knight()};
    }
}