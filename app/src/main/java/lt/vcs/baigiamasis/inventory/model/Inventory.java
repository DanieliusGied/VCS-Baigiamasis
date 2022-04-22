package lt.vcs.baigiamasis.inventory.model;

import static lt.vcs.baigiamasis.inventory.model.ItemType.ARMOR;
import static lt.vcs.baigiamasis.inventory.model.ItemType.WEAPON;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;

import lt.vcs.baigiamasis.Constant;

@Entity(tableName = Constant.ENTITY_INVENTORY_TABLE)
public class Inventory {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "inventory_item_is_equipped")
    private boolean isEquipped;
    @ColumnInfo(name = "inventory_player_id")
    private int characterId;
    @ColumnInfo(name = "inventory_item_id")
    private int itemId;

    public Inventory(boolean isEquipped, int characterId, int itemId) {
        this.isEquipped = isEquipped;
        this.characterId = characterId;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
