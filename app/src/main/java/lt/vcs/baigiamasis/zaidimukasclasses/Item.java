package lt.vcs.baigiamasis.zaidimukasclasses;

import static lt.vcs.baigiamasis.MainActivity.random;

import androidx.room.*;

@Entity(tableName = Constant.ENTITY_ITEM_TABLE)
public class Item {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "item_character_id")
    public int characterId;
    @ColumnInfo(name = "item_name")
    private String name;
    @ColumnInfo(name = "item_type")
    private ItemType itemType;
    @ColumnInfo(name = "item_armor")
    private int armor;
    @ColumnInfo(name = "item_max_damage")
    private int maxDamage;
    @ColumnInfo(name = "item_price")
    private int price;
    @ColumnInfo(name = "item_is_equipped")
    private boolean isEquipped;

    @Ignore
    private int damage;

    public Item(String name, ItemType itemType, int maxDamage, int armor, int price) {
        this.name = name;
        this.itemType = itemType;
        this.maxDamage = maxDamage;
        this.armor = armor;
        this.price = price;
        this.characterId = Constant.CHARACTER_ID;
    }

    public int calculateWeaponDamage(){
        this.damage = random.nextInt(maxDamage);
        return this.damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getMaxDamage() {
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

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }
}
