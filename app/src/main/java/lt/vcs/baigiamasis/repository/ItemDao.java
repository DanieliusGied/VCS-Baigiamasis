package lt.vcs.baigiamasis.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.vcs.baigiamasis.zaidimukasclasses.Constant;
import lt.vcs.baigiamasis.zaidimukasclasses.Item;

@Dao
public interface ItemDao {

    boolean isTrue = true;
    boolean isFalse = false;

    @Query("SELECT * FROM " + Constant.ENTITY_ITEM_TABLE)
    List<Item> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_ITEM_TABLE + " WHERE id =:id")
    Item getItem(int id);

    @Query("SELECT * FROM " + Constant.ENTITY_ITEM_TABLE + " WHERE item_character_id =:characterId AND item_is_equipped = 1 AND item_type = 'WEAPON'")
    Item getEquippedWeapon(int characterId);

    @Query("SELECT * FROM " + Constant.ENTITY_ITEM_TABLE + " WHERE item_character_id =:characterId AND item_is_equipped = 1 AND item_type = 'ARMOR'")
    Item getEquippedArmor(int characterId);

    @Query("SELECT * FROM " + Constant.ENTITY_ITEM_TABLE + " WHERE item_is_equipped = 0")
    List<Item> getAllNotEquipped();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(List<Item> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Delete
    void deleteItem(List<Item> items);

    @Query("DELETE FROM " + Constant.ENTITY_ITEM_TABLE + " WHERE id =:id")
    void deleteItem(int id);
}
