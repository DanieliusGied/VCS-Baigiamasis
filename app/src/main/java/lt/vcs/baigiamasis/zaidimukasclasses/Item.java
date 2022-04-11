package lt.vcs.baigiamasis.zaidimukasclasses;

import static lt.vcs.zaidimukas.Main.random;

public class Item {
    String name;
    ItemType itemType;
    int armor;
    int damage;
    int maxDamage;
    int price;

    public Item(String name, ItemType itemType, int maxDamage, int price) {
        this.name = name;
        this.itemType = itemType;
        this.maxDamage = maxDamage;
        this.price = price;
    }

    public Item(String name, int armor, ItemType itemType, int price) {
        this.name = name;
        this.itemType = itemType;
        this.armor = armor;
        this.price = price;
    }

    public int calculateWeaponDamage(){
        this.damage = random.nextInt(maxDamage);
        return this.damage;
    }
}
