package lt.vcs.baigiamasis.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.vcs.baigiamasis.zaidimukasclasses.Character;
import lt.vcs.baigiamasis.zaidimukasclasses.Constant;
import lt.vcs.baigiamasis.zaidimukasclasses.Enemy;

@Dao
public interface EnemyDao {
    @Query("SELECT * FROM " + Constant.ENTITY_ENEMY_TABLE)
    List<Enemy> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_ENEMY_TABLE + " WHERE id =:id")
    Enemy getItem(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(List<Enemy> enemies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(Enemy enemy);

    @Delete
    void deleteCharacter(Enemy enemy);

    @Delete
    void deleteCharacter(List<Enemy> enemies);

    @Query("DELETE FROM " + Constant.ENTITY_ENEMY_TABLE + " WHERE id =:id")
    void deleteItem(int id);
}
