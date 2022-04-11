package lt.vcs.baigiamasis.zaidimukasclasses;

import java.util.ArrayList;
import java.util.HashMap;

import static lt.vcs.baigiamasis.zaidimukasclasses.ItemType.ARMOR;
import static lt.vcs.baigiamasis.zaidimukasclasses.ItemType.WEAPON;
import static lt.vcs.baigiamasis.MainActivity.*;

public class Character {
    private String name;
    private int statStr;
    private int statCon;
    private int statDex;
    private int level;
    private int maxHealth;
    private int currentHealth;
    private HashMap<ItemType, Item> inventory;
    private int damage;
    private int armor;
    private int gold;

    public Character(String name){
        System.out.println("Hello and welcome :) Please enter your character's name:");
        this.name = name;
        inventory = new HashMap<ItemType, Item>();
        inventory.put(WEAPON, dagger);
        inventory.put(ARMOR, clothShirt);
        level = 1;

        this.statStr = 10;
        this.statCon = 10;
        this.statDex = 10;

        setMaxHealth();
        setCurrentHealth();
        setArmor();
        setGold();
    }

    public void setGold(){
        this.gold = 10;
    }

    public void setMaxHealth(){
        this.maxHealth = this.level * 5 + (this.statCon - 10) * 5;
    }

    public void setCurrentHealth(){
        this.currentHealth = this.maxHealth;
    }

    public void setArmor(){
        this.armor = inventory.get(ARMOR).armor + (this.statDex - 10);
    }

    public int calculateDamage(){
        this.damage = inventory.get(WEAPON).calculateWeaponDamage() + (this.statStr - 10);
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

//    public void getCharacterData(){
//        System.out.println("");
//        System.out.println("Character name: " + this.name);
//        System.out.println("Character level: " + this.level);
//        System.out.println("Character HP: " + this.maxHealth);
//        System.out.println("Character STR: " + this.stats[0]);
//        System.out.println("Character CON: " + this.stats[1]);
//        System.out.println("Character DEX: " + this.stats[2]);
//        System.out.println("Character Armor: " + this.armor);
//        System.out.println("Character Damage: " + "1 - " + (inventory.get(WEAPON).maxDamage + this.stats[0] - 10));
//    }

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

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getArmor() {
        return armor;
    }

    public int getGold() {
        return gold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getStatStr() {
        return statStr;
    }

    public void setStatStr(int statStr) {
        this.statStr = statStr;
    }

    public int getStatCon() {
        return statCon;
    }

    public void setStatCon(int statCon) {
        this.statCon = statCon;
    }

    public int getStatDex() {
        return statDex;
    }

    public void setStatDex(int statDex) {
        this.statDex = statDex;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
