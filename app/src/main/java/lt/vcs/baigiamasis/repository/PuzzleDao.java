package lt.vcs.baigiamasis.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.puzzle.model.Puzzle;
import lt.vcs.baigiamasis.inventory.model.Item;

@Dao
public interface PuzzleDao {

    @Query("SELECT * FROM " + Constant.ENTITY_PUZZLE_TABLE)
    List<Puzzle> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_PUZZLE_TABLE + " WHERE id =:id")
    Puzzle getItem(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(List<Puzzle> puzzleList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Puzzle puzzle);

    @Delete
    void deleteItem(Puzzle puzzle);

    @Delete
    void deleteItem(List<Puzzle> puzzleList);

    @Query("DELETE FROM " + Constant.ENTITY_PUZZLE_TABLE + " WHERE id =:id")
    void deleteItem(int id);
}
