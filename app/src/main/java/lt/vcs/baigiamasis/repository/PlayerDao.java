package lt.vcs.baigiamasis.repository;

import androidx.room.*;

import java.util.List;

import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.Constant;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM " + Constant.ENTITY_PLAYER_TABLE)
    List<Player> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_PLAYER_TABLE + " WHERE id =:id")
    Player getItem(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItems(List<Player> players);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Player player);

    @Delete
    void deleteItem(Player player);

    @Delete
    void deleteItem(List<Player> players);

    @Query("DELETE FROM " + Constant.ENTITY_PLAYER_TABLE + " WHERE id =:id")
    void deleteItem(int id);

    @Query("SELECT MAX(id) FROM " + Constant.ENTITY_PLAYER_TABLE)
    int returnMaxID();
}
