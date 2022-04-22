package lt.vcs.baigiamasis.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.vcs.baigiamasis.Constant;
import lt.vcs.baigiamasis.inventory.model.Inventory;
import lt.vcs.baigiamasis.inventory.model.Item;

@Dao
public interface InventoryDao {

    @Query("SELECT * FROM " + Constant.ENTITY_INVENTORY_TABLE)
    List<Inventory> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_INVENTORY_TABLE + " WHERE id =:id")
    Inventory getItem(int id);

    @Query("SELECT * FROM " + Constant.ENTITY_INVENTORY_TABLE + " WHERE inventory_player_id =:id")
    List<Inventory> getAllFromCharacter(int id);

    @Query("SELECT item.* FROM "
            + Constant.ENTITY_INVENTORY_TABLE
            + " inventory LEFT JOIN " + Constant.ENTITY_ITEM_TABLE
            + " item ON inventory.inventory_item_id = item.id " +
            "WHERE inventory.inventory_player_id =:characterId AND inventory.inventory_item_is_equipped = 1")
    List<Item> getAllFromCharacterEquipped(int characterId);

    @Query("SELECT item.* FROM "
            + Constant.ENTITY_INVENTORY_TABLE
            + " inventory LEFT JOIN " + Constant.ENTITY_ITEM_TABLE
            + " item ON inventory.inventory_item_id = item.id " +
            "WHERE inventory.inventory_player_id =:characterId AND inventory.inventory_item_is_equipped = 0")
    List<Item> getAllFromCharacterNotEquipped(int characterId);

    @Query("SELECT item.* FROM "
            + Constant.ENTITY_INVENTORY_TABLE
            + " inventory LEFT JOIN " + Constant.ENTITY_ITEM_TABLE
            + " item ON inventory.inventory_item_id = item.id " +
            "WHERE inventory.inventory_player_id =:characterId AND item.item_type = 'WEAPON' AND inventory.inventory_item_is_equipped = 1")
    Item getWeaponFromCharacter(int characterId);

    @Query("SELECT item.* FROM "
            + Constant.ENTITY_INVENTORY_TABLE
            + " inventory LEFT JOIN " + Constant.ENTITY_ITEM_TABLE
            + " item ON inventory.inventory_item_id = item.id " +
            "WHERE inventory.inventory_player_id =:characterId AND item.item_type = 'ARMOR' AND inventory.inventory_item_is_equipped = 1")
    Item getArmorFromCharacter(int characterId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(List<Inventory> inventoryList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Inventory inventory);

    @Delete
    void deleteItem(Inventory inventory);

    @Delete
    void deleteItem(List<Inventory> inventoryList);

    @Query("DELETE FROM " + Constant.ENTITY_INVENTORY_TABLE + " WHERE id =:id")
    void deleteItem(int id);

    @Query("DELETE FROM " + Constant.ENTITY_INVENTORY_TABLE + " WHERE inventory_player_id =:id")
    void deleteItemFromCharacter(int id);

    @Query("SELECT inventory.id FROM "
            + Constant.ENTITY_INVENTORY_TABLE
            + " inventory LEFT JOIN " + Constant.ENTITY_ITEM_TABLE
            + " item ON inventory.inventory_item_id = item.id " +
            "WHERE inventory.inventory_player_id =:characterId AND item.item_type = 'WEAPON'")
    int returnWeaponId(int characterId);

    @Query("SELECT inventory.id FROM "
            + Constant.ENTITY_INVENTORY_TABLE
            + " inventory LEFT JOIN " + Constant.ENTITY_ITEM_TABLE
            + " item ON inventory.inventory_item_id = item.id " +
            "WHERE inventory.inventory_player_id =:characterId AND item.item_type = 'ARMOR'")
    int returnArmorId(int characterId);
}
