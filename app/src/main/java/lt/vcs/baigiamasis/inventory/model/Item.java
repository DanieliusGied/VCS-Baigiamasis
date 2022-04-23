package lt.vcs.baigiamasis.inventory.model;

import androidx.room.*;

import lt.vcs.baigiamasis.common.Constant;

@Entity(tableName = Constant.ENTITY_ITEM_TABLE)
public class Item {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "item_name")
    private String name;
    @ColumnInfo(name = "item_type")
    private String itemType;
    @ColumnInfo(name = "item_armor")
    private Integer armor;
    @ColumnInfo(name = "item_max_damage")
    private Integer maxDamage;
    @ColumnInfo(name = "item_price")
    private int price;

    public Item(int id, String name, String itemType, int maxDamage, int armor, int price) {
        this.id = id;
        this.name = name;
        this.itemType = itemType;
        this.maxDamage = maxDamage;
        this.armor = armor;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public Integer getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        switch (itemType){
            case Constant.WEAPON:
                return name + ", weapon, base damage: 1-" + maxDamage;
            case Constant.ARMOR:
                return name + ", armor, base armor: " + armor;
            default:
                return "ERROR";
        }
    }
}
