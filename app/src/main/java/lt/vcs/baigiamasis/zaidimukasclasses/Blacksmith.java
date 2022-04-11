package lt.vcs.baigiamasis.zaidimukasclasses;

import java.util.InputMismatchException;

import static lt.vcs.zaidimukas.Main.*;

public class Blacksmith {

    public void run(){
        generatePicture();
        welcome();
        openShop();
    }

    public void generatePicture(){
        pageBreak();
        System.out.println("");
        System.out.println("  .-------..___");
        System.out.println("  '-._     :_.-'");
        System.out.println("      ) _ (    ");
        System.out.println("     '-' '-'    ");
    }

    public void welcome(){
        System.out.println("----------------------------");
        System.out.println("Welcome to the blacksmith!");
        System.out.println("----------------------------");
    }

    public void openShop(){
        System.out.println("What are you interested in?");
        System.out.println("1 - Weapons");
        System.out.println("2 - Armor -     NOT SUPPORTED");
        System.out.println("ELSE - EXIT SHOP");
        pageBreak();

        switch (scannerSelect()){
            case 1:
                openShopWeapons();
                break;
            case 2:
                openShopArmor();
                break;
            default:
                break;
        }
    }

    public void openShopWeapons(){
        System.out.println("----------------------------");
        System.out.println("Here are the weapons available!");
        System.out.println("Which one would you like to see?");
        System.out.println("----------------------------");
        System.out.println("1 - Dagger");
        System.out.println("2 - Sword");
        System.out.println("3 - Longsword");
        System.out.println("4 - Greatsword");
        pageBreak();

        switch(scannerSelect()){
            case 1:
                generateDaggerImage();
                openPurchaseWindow(dagger);
                break;
            case 2:
                generateSwordImage();
                openPurchaseWindow(sword);
                break;
            case 3:
                generateLongSwordImage();
                openPurchaseWindow(longSword);
                break;
            case 4:
                generateGreatSwordImage();
                openPurchaseWindow(greatSword);
                break;
            default:
                openShop();
                break;
        }
       //make each openable and show a picture / price / stats
    }

    public void openShopArmor(){
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    public void generateDaggerImage(){
        System.out.println("----------------------------");
        System.out.println("|                          |");
        System.out.println("|           -|----         |");
        System.out.println("|                          |");
        System.out.println("----------------------------");

    }

    public void generateSwordImage(){
        System.out.println("----------------------------");
        System.out.println("|                          |");
        System.out.println("|    ====)-------------    |");
        System.out.println("|                          |");
        System.out.println("----------------------------");
    }

    public void generateLongSwordImage(){
        System.out.println("---------------------------------------");
        System.out.println("|                                     |");
        System.out.println("|  [==|::::::::::::::::::::::::::::/  |");
        System.out.println("|                                     |");
        System.out.println("---------------------------------------");
    }

    public void generateGreatSwordImage(){
        System.out.println("--------------------------------------------------");
        System.out.println("|           #>_________________________________  |");
        System.out.println("|  [########[]_________________________________> |");
        System.out.println("|           #>                                   |");
        System.out.println("--------------------------------------------------");
    }

    public void openPurchaseWindow(Item item) {
        System.out.println("Price: " + item.price);
        System.out.println("Damage: 1 - " + item.maxDamage);
        System.out.println("1 - BUY");
        System.out.println("ELSE - EXIT");
        pageBreak();

        switch (scannerSelect()) {
            case 1:
                purchase(player, item);
                break;
            default:
                openShop();
                break;
        }
    }

    public void purchase(Character player, Item item){
            if (player.gold >= item.price){
                player.gold -= item.price;
                player.inventory.put(item.itemType, item);
                System.out.println(item.name + " was purchased and equipped!");
            } else {
                System.out.println("Not enough gold!");
            }
            pageBreak();
            openShop();

        }
}
