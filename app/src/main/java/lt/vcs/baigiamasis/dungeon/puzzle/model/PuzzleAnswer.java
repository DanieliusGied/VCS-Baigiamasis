package lt.vcs.baigiamasis.dungeon.puzzle.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lt.vcs.baigiamasis.common.Constant;

@Entity(tableName = Constant.ENTITY_PUZZLE_ANSWER_TABLE)
public class PuzzleAnswer {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "puzzle_answer_text")
    private String answer;
    @ColumnInfo(name = "puzzle_answer_is_correct")
    boolean isCorrect;

    public PuzzleAnswer(int id, String answer, boolean isCorrect) {
        this.id = id;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
