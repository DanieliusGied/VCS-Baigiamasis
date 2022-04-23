package lt.vcs.baigiamasis.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.puzzle.model.Puzzle;
import lt.vcs.baigiamasis.dungeon.puzzle.model.PuzzleAnswer;

@Dao
public interface PuzzleAnswerDao {

    @Query("SELECT * FROM " + Constant.ENTITY_PUZZLE_ANSWER_TABLE)
    List<PuzzleAnswer> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_PUZZLE_ANSWER_TABLE + " WHERE id =:id")
    PuzzleAnswer getItem(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(List<PuzzleAnswer> puzzleList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(PuzzleAnswer puzzle);

    @Delete
    void deleteItem(PuzzleAnswer puzzle);

    @Delete
    void deleteItem(List<PuzzleAnswer> puzzleList);

    @Query("DELETE FROM " + Constant.ENTITY_PUZZLE_ANSWER_TABLE + " WHERE id =:id")
    void deleteItem(int id);
}
