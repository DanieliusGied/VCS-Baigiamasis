package lt.vcs.baigiamasis.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.combat.model.Graveyard;

@Dao
public interface GraveyardDao {

    @Query("SELECT * FROM " + Constant.ENTITY_GRAVEYARD_TABLE)
    List<Graveyard> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_GRAVEYARD_TABLE + " WHERE id =:id")
    Graveyard getItem(int id);

    @Query("SELECT * FROM " + Constant.ENTITY_GRAVEYARD_TABLE + " WHERE graveyard_player_id =:id")
    List<Graveyard> getAllFromCharacter(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(List<Graveyard> graveyardList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Graveyard graveyard);

    @Delete
    void deleteItem(Graveyard graveyard);

    @Delete
    void deleteItem(List<Graveyard> graveyardList);

    @Query("DELETE FROM " + Constant.ENTITY_GRAVEYARD_TABLE + " WHERE id =:id")
    void deleteItem(int id);

    @Query("DELETE FROM " + Constant.ENTITY_GRAVEYARD_TABLE + " WHERE graveyard_player_id =:id")
    void deleteItemFromCharacter(int id);
}
