package lt.vcs.baigiamasis.zaidimukasclasses;

import java.util.HashMap;

import static lt.vcs.baigiamasis.zaidimukasclasses.ItemType.ARMOR;
import static lt.vcs.baigiamasis.zaidimukasclasses.ItemType.WEAPON;
import static lt.vcs.baigiamasis.MainActivity.*;

public class Character {
    public String name;
    public int[] stats = new int[3];
    public int level;
    public int health;
    public HashMap<ItemType, Item> inventory;
    static public int damage;
    static public int armor;
    static int gold;

    public Character(String name){
        System.out.println("Hello and welcome :) Please enter your character's name:");
        this.name = name;
        inventory = new HashMap<ItemType, Item>();
        inventory.put(WEAPON, dagger);
        inventory.put(ARMOR, clothShirt);
        level = 1;
        setStats(10, 10, 10);
        setHealth();
        setArmor();
        setGold();
    }

    public String getName() {
        return name;
    }

    public void setStats(int str, int con, int dex){
        this.stats[0] = str;
        this.stats[1] = con;
        this.stats[2] = dex;
    }

    public void setGold(){
        this.gold = 10;
    }

    public void setHealth(){
        this.health = this.level * 5 + (this.stats[1] - 10) * 5;
    }

    public void setArmor(){
        this.armor = inventory.get(ARMOR).armor + (this.stats[2] - 10);
    }

    public int calculateDamage(){
        this.damage = inventory.get(WEAPON).calculateWeaponDamage() + (stats[0] - 10);
        return this.damage;
    }

//    public void increaseStats(){
//        System.out.println("");
//        System.out.println("You have 1 ability point! Increase STR or CON:");
//        System.out.println("1 - STR");
//        System.out.println("2 - CON");
//        boolean run = true;
//        while (run){
//            int i = scannerSelect();
//            switch (i){
//                case 1:
//                    stats[0]++;
//                    System.out.println("STR increased to " + stats[0] + "!");
//                    run = false;
//                    break;
//                case 2:
//                    stats[1]++;
//                    System.out.println("CON increased to " + stats[1] + "!");
//                    run = false;
//                    break;
//                default:
//                    System.out.println("Unrecognised input!");
//                    break;
//            }
//        }
//    }

//    public void levelUp(){
//        level++;
//        increaseStats();
//        setHealth();
//    }

    public void getCharacterData(){
        System.out.println("");
        System.out.println("Character name: " + this.name);
        System.out.println("Character level: " + this.level);
        System.out.println("Character HP: " + this.health);
        System.out.println("Character STR: " + this.stats[0]);
        System.out.println("Character CON: " + this.stats[1]);
        System.out.println("Character DEX: " + this.stats[2]);
        System.out.println("Character Armor: " + this.armor);
        System.out.println("Character Damage: " + "1 - " + (inventory.get(WEAPON).maxDamage + this.stats[0] - 10));
    }

//    public void death(){
//        System.out.println("Oh no! Your character has died!");
//        System.out.println("");
//        System.out.println("      ██     ");
//        System.out.println("  ▓▓▓▓██▓▓▓▓");
//        System.out.println("  ░░░░██░░  ");
//        System.out.println("      ██  ");
//        System.out.println("      ██  ");
//        System.out.println("    ▒▒████");
//        System.out.println("  ▓▓██████▓▓");
//        System.out.println("");
//        System.out.println("GAME OVER!");
//        System.exit(0);
//    }

    public void writeCharacterToDatabase(){
        // TODO: 4/11/2022 CREATE THE WRITE TO DATABASE METHOD
    }
}
