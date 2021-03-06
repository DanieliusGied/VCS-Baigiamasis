package lt.vcs.baigiamasis.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.model.Dungeon;
import lt.vcs.baigiamasis.dungeon.model.Encounter;

@Dao
public interface DungeonDao {

    @Query("SELECT * FROM " + Constant.ENTITY_DUNGEON_TABLE)
    List<Dungeon> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_DUNGEON_TABLE + " WHERE id =:id")
    Dungeon getItem(int id);

    @Query("SELECT * FROM " + Constant.ENTITY_DUNGEON_TABLE + " WHERE dungeon_player_id =:id")
    Dungeon getItemFromCharacter(int id);

    @Query("SELECT * FROM " + Constant.ENTITY_DUNGEON_TABLE + " WHERE dungeon_player_id =:id")
    List<Dungeon> getAllFromCharacter(int id);

    @Query("SELECT encounter.* FROM "
            + Constant.ENTITY_DUNGEON_TABLE
            + " dungeon LEFT JOIN " + Constant.ENTITY_ENCOUNTER_TABLE
            + " encounter ON dungeon.dungeon_encounter_id = encounter.id " +
            "WHERE dungeon.dungeon_player_id =:characterId")
    Encounter getEncounterFromCharacter(int characterId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(List<Dungeon> inventoryList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Dungeon dungeon);

    @Delete
    void deleteItem(Dungeon dungeon);

    @Delete
    void deleteItem(List<Dungeon> dungeonList);

    @Query("DELETE FROM " + Constant.ENTITY_DUNGEON_TABLE + " WHERE id =:id")
    void deleteItem(int id);

    @Query("DELETE FROM " + Constant.ENTITY_DUNGEON_TABLE + " WHERE dungeon_player_id =:id")
    void deleteItemFromCharacter(int id);
}
