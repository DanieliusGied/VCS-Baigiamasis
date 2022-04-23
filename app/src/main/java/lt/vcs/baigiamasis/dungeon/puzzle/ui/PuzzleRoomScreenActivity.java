package lt.vcs.baigiamasis.dungeon.puzzle.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.dungeon.puzzle.model.Puzzle;
import lt.vcs.baigiamasis.dungeon.puzzle.model.PuzzleAnswer;

public class PuzzleRoomScreenActivity extends AppCompatActivity {

    private List<Puzzle> puzzleList;
    private List<PuzzleAnswer> puzzleAnswerList;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    private TextView textViewDescription;
    private TextView textViewPuzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_room_screen);
    }


    // one room is riddles, another room is math

    // every time this room is loaded we go through the puzzle answer list and check each answer as false. Then we select a random riddle and make that answer right
}