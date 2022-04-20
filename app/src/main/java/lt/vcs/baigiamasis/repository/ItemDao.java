package lt.vcs.baigiamasis.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.vcs.baigiamasis.Constant;
import lt.vcs.baigiamasis.inventory.model.Item;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM " + Constant.ENTITY_ITEM_TABLE)
    List<Item> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_ITEM_TABLE + " WHERE id =:id")
    Item getItem(int id);

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
