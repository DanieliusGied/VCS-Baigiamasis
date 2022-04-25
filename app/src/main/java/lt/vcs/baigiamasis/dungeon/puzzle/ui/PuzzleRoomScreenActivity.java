package lt.vcs.baigiamasis.dungeon.puzzle.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.combat.model.Combat;
import lt.vcs.baigiamasis.dungeon.combat.model.Graveyard;
import lt.vcs.baigiamasis.dungeon.combat.ui.CombatScreenActivity;
import lt.vcs.baigiamasis.dungeon.model.Dungeon;
import lt.vcs.baigiamasis.dungeon.model.Encounter;
import lt.vcs.baigiamasis.dungeon.puzzle.model.Puzzle;
import lt.vcs.baigiamasis.dungeon.puzzle.model.PuzzleAnswer;
import lt.vcs.baigiamasis.dungeon.ui.ExploreDungeonScreenActivity;
import lt.vcs.baigiamasis.enemy.model.Enemy;
import lt.vcs.baigiamasis.mainmenu.ui.MainActivity;
import lt.vcs.baigiamasis.mainmenu.ui.MainGameMenuScreenActivity;
import lt.vcs.baigiamasis.mainmenu.ui.PlayerSelectScreenActivity;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.repository.DungeonDao;
import lt.vcs.baigiamasis.repository.EncounterDao;
import lt.vcs.baigiamasis.repository.EnemyDao;
import lt.vcs.baigiamasis.repository.GraveyardDao;
import lt.vcs.baigiamasis.repository.InventoryDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.repository.PlayerDao;
import lt.vcs.baigiamasis.repository.PuzzleAnswerDao;
import lt.vcs.baigiamasis.repository.PuzzleDao;

public class PuzzleRoomScreenActivity extends AppCompatActivity {

    private List<Puzzle> puzzleList;
    private List<PuzzleAnswer> puzzleAnswerList;
    private List<PuzzleAnswer> puzzleAnswerChoiceList;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    private TextView textViewDescription;
    private TextView textViewPuzzle;

    private MainDatabase mainDatabase;
    private PlayerDao playerDao;
    private DungeonDao dungeonDao;
    private EncounterDao encounterDao;
    private PuzzleDao puzzleDao;
    private PuzzleAnswerDao puzzleAnswerDao;

    private Player player;
    private Dungeon dungeon;
    private Encounter encounter;
    private Puzzle chosenPuzzle;
    private PuzzleAnswer chosenPuzzleAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDatabase();
        setUpUI();
    }

    //SET-UP DATABASE
    private void setUpDatabase(){

        Intent intent = getIntent();
        int characterID = intent.getIntExtra(Constant.PLAYER, 0);
        int encounterID = intent.getIntExtra(Constant.RANDOM_DUNGEON, 0);

        mainDatabase = MainDatabase.getInstance(getApplicationContext());
        playerDao = mainDatabase.playerDao();
        dungeonDao = mainDatabase.dungeonDao();
        encounterDao = mainDatabase.encounterDao();
        puzzleAnswerDao = mainDatabase.puzzleAnswerDao();
        puzzleDao = mainDatabase.puzzleDao();

        player = playerDao.getItem(characterID);
        encounter = encounterDao.getItem(encounterID);
        dungeon = dungeonDao.getItemFromCharacter(player.getId());
        puzzleList = puzzleDao.getAll();
        puzzleAnswerList = puzzleAnswerDao.getAll();
    }

    //SET-UP UI
    private void setUpUI(){
        setContentView(R.layout.activity_puzzle_room_screen);

        setUpPuzzle();
        setUpText();
        setUpProgressBar();
        setUpAnswerChoiceListView();
        setUpListViewItemClick();
    }

    private void setUpPuzzle(){
        Random random = new Random();
        int randomInt = random.nextInt(puzzleList.size());

        chosenPuzzle = puzzleList.get(randomInt);
        chosenPuzzleAnswer = puzzleAnswerList.get(randomInt);
        chosenPuzzleAnswer.setCorrect(true);

        populateAnswerList(randomInt);
    }

    private void setUpText(){
        TextView textViewHP = findViewById(R.id.textViewPuzzleRoomHP);

        textViewDescription  = findViewById(R.id.textViewPuzzleRoomDescription);
        textViewDescription.setText(String.format(getResources().getString(R.string.puzzle_screen_description), player.getName()));

        textViewPuzzle = findViewById(R.id.textViewPuzzlePuzzle);
        textViewPuzzle.setText(String.format(getResources().getString(R.string.puzzle_screen_puzzle_text), chosenPuzzle.getPuzzle()));

        textViewHP.setText(String.format(getResources().getString(R.string.combat_screen_hp), player.getCurrentHealth(), player.getMaxHealth()));
    }

    private void populateAnswerList(int randomInt){
        puzzleAnswerChoiceList = puzzleAnswerList;
        puzzleAnswerChoiceList.remove(randomInt);
        Collections.shuffle(puzzleAnswerChoiceList);

        puzzleAnswerChoiceList.subList(0, 7).clear();

        puzzleAnswerChoiceList.add(chosenPuzzleAnswer);
        Collections.shuffle(puzzleAnswerChoiceList);
    }

    private void setUpProgressBar(){
        ProgressBar progressBarPlayerHP = findViewById(R.id.progressBarPuzzleRoomHP);

        int progressPlayerHP = (int) Math.round((double) player.getCurrentHealth() / (double) player.getMaxHealth() * 100);
        progressBarPlayerHP.setProgress(progressPlayerHP);
    }

    private void setUpAnswerChoiceListView(){
        listView = findViewById(R.id.listViewPuzzleRoomAnswers);

        arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                puzzleAnswerChoiceList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView item = (TextView) super.getView(position,convertView,parent);

                item.setTypeface(getResources().getFont(R.font.vecna_bold_italic));
                item.setTextColor(getResources().getColor(R.color.background_brown));
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

                return item;
            }
        };;

        listView.setAdapter(arrayAdapter);
    }

    private void setUpListViewItemClick() {
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            PuzzleAnswer clickedAnswer = puzzleAnswerChoiceList.get(position);
            if (!clickedAnswer.isCorrect()) {
                showErrorDialogIncorrectChoice();
            }

            if (clickedAnswer.isCorrect()) {
                showErrorDialogCorrectChoice();
            }
        });
    }

    public void showErrorDialogIncorrectChoice(){
        final Dialog dialog = new Dialog(PuzzleRoomScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        textView.setText(String.format(getResources().getString(
                R.string.puzzle_screen_incorrect_choice),
                player.getName()
        ));

        buttonPositive.setText(R.string.button_close);
        buttonPositive.setOnClickListener(view -> {
            player.setCurrentHealth(player.getCurrentHealth() - 2);
            playerDao.insertItem(player);
            if (player.getCurrentHealth() <= 0){
                player.setCurrentHealth(0);
                showErrorDialogPlayerDeath();
            } else {
                recreate();
            }
        });

        buttonNegative.setEnabled(false);
        buttonNegative.setVisibility(View.INVISIBLE);

        dialog.show();
    }

    public void showErrorDialogCorrectChoice(){
        final Dialog dialog = new Dialog(PuzzleRoomScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        textView.setText(R.string.puzzle_screen_correct_choice);

        buttonPositive.setText(R.string.button_proceed);
        buttonPositive.setOnClickListener(view -> {
            player.setCurrentXP(player.getCurrentXP() + 1);
            playerDao.insertItem(player);

            dungeon.setDungeonProgress(dungeon.getDungeonProgress() + 1);
            dungeonDao.insertItem(dungeon);

            Intent intent = new Intent(PuzzleRoomScreenActivity.this, ExploreDungeonScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());

            startActivity(intent);
            finish();
        });

        buttonNegative.setEnabled(false);
        buttonNegative.setVisibility(View.INVISIBLE);

        dialog.show();
    }

    private void showErrorDialogPlayerDeath(){
        final Dialog dialog = new Dialog(PuzzleRoomScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        textView.setText(String.format(
                getResources().getString(R.string.combat_screen_player_defeat),
                player.getName()
        ));

        buttonPositive.setText(R.string.button_close);
        buttonPositive.setOnClickListener(view -> {
            Graveyard graveyard = new Graveyard(player.getId(), player.getName(), player.getLevel());
            GraveyardDao graveyardDao = mainDatabase.graveyardDao();
            graveyardDao.insertItem(graveyard);

            playerDao.deleteItem(player);
            dungeonDao.deleteItemFromCharacter(player.getId());

            InventoryDao inventoryDao = mainDatabase.inventoryDao();
            inventoryDao.deleteItemFromCharacter(player.getId());

            Intent intent = new Intent(PuzzleRoomScreenActivity.this, MainActivity.class);
            intent.putExtra(PLAYER, player.getId());

            startActivity(intent);
            finish();
        });

        buttonNegative.setEnabled(false);
        buttonNegative.setVisibility(View.INVISIBLE);

        dialog.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(PuzzleRoomScreenActivity.this);

        builder.setMessage(String.format(getResources().getString(R.string.combat_screen_player_defeat), player.getName()));

        builder.setPositiveButton("Close",
                (dialogInterface, i) -> {
                    Graveyard graveyard = new Graveyard(player.getId(), player.getName(), player.getLevel());

                    GraveyardDao graveyardDao = mainDatabase.graveyardDao();
                    InventoryDao inventoryDao = mainDatabase.inventoryDao();

                    graveyardDao.insertItem(graveyard);
                    playerDao.deleteItem(player);
                    dungeonDao.deleteItemFromCharacter(player.getId());
                    inventoryDao.deleteItemFromCharacter(player.getId());

                    Intent intent = new Intent(PuzzleRoomScreenActivity.this, MainActivity.class);
                    intent.putExtra(PLAYER, player.getId());

                    startActivity(intent);
                    finish();
                });
        builder.show();
    }


    // one room is riddles, another room is math

    // every time this room is loaded we go through the puzzle answer list and check each answer as false. Then we select a random riddle and make that answer right
}