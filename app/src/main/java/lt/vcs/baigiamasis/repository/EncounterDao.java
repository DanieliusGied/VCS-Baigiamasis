package lt.vcs.baigiamasis.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.vcs.baigiamasis.Constant;
import lt.vcs.baigiamasis.dungeon.model.Encounter;
import lt.vcs.baigiamasis.inventory.model.Item;

@Dao
public interface EncounterDao {

    @Query("SELECT * FROM " + Constant.ENTITY_ENCOUNTER_TABLE)
    List<Encounter> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_ENCOUNTER_TABLE + " WHERE id =:id")
    Encounter getItem(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(List<Encounter> encounters);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Encounter encounter);

    @Delete
    void deleteItem(Encounter encounter);

    @Delete
    void deleteItem(List<Encounter> encounter);

    @Query("DELETE FROM " + Constant.ENTITY_ENCOUNTER_TABLE + " WHERE id =:id")
    void deleteItem(int id);
}
