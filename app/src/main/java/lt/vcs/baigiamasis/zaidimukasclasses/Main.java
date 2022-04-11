package lt.vcs.baigiamasis.zaidimukasclasses;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import static lt.vcs.zaidimukas.ItemType.ARMOR;
import static lt.vcs.zaidimukas.ItemType.WEAPON;

public class Main {

    public static java.lang.Character player = new java.lang.Character();
    public static Blacksmith blacksmith = new Blacksmith();
    public static Dungeon dungeon = new Dungeon();
    public static Room room = new Room();
    public static Room roomPuzzle = new RoomPuzzle();
    public static Room roomCombat = new RoomCombat();
    public static Room roomTreasure = new RoomTreasure();
    public static Room roomBoss = new RoomBoss();
    public static Random random = new Random();
    public static Scanner scanner = new Scanner(System.in);
    public static Enemy enemy = new Enemy();
    public static Enemy goblin = new Goblin();
    public static Combat combat = new Combat();
    public static Item dagger = new Item("Dagger", WEAPON, 4, 0);
    public static Item sword = new Item("Sword", WEAPON, 6, 10);
    public static Item longSword = new Item("Longsword", WEAPON, 8, 25);
    public static Item greatSword = new Item("Greatsword", WEAPON, 10, 50);
    public static Item clothShirt = new Item("Dagger", 8, ARMOR, 0);
    public static Item leatherTunic = new Item("Dagger", 10, ARMOR, 10);
    public static Item chainVest = new Item("Dagger", 12, ARMOR, 25);
    public static Item plateMail = new Item("Dagger", 15, ARMOR, 50);

    public static void main(String[] args) {
        // write your code here
        player.initializeCharacter();
        play(player);
    }

    //method used for all decisions in town:
    public static void play(java.lang.Character player) {
        boolean run = true;
        while (run) {
            System.out.println("");
            System.out.println("What would you like to do?");
            System.out.println("1 - Visit the blacksmith");
            System.out.println("2 - Go fight!");
            System.out.println("3 - Check character information.");
//            System.out.println("4 - Check character spells. - NOT SUPPORTED");
//            System.out.println("5 - Check Inventory. - NOT SUPPORTED");
            System.out.println("ELSE - EXIT GAME");
            pageBreak();

            int selection = scannerSelect();
            switch (selection) {
                case 1:
                    blacksmith.run();
                    break;
                case 2:
                    dungeon.initialize();
                    break;
                case 3:
                    player.getCharacterData();
                    break;
                default:
                    System.out.println("Goodbye!");
                    run = false;
                    break;
            } // cia turi fight pereit i dungeon
        }
    }

    public static void pageBreak(){
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }

    public static int scannerSelect(){
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e){
            scanner.nextLine();
            return 0;
        }
    }
}