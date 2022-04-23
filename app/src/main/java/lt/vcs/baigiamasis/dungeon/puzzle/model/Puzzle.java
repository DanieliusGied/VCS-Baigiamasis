package lt.vcs.baigiamasis.dungeon.puzzle.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lt.vcs.baigiamasis.common.Constant;

@Entity(tableName = Constant.ENTITY_PUZZLE_TABLE)
public class Puzzle {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "puzzle_text")
    private String puzzle;

    public Puzzle(int id, String puzzle) {
        this.id = id;
        this.puzzle = puzzle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(String puzzle) {
        this.puzzle = puzzle;
    }
}
