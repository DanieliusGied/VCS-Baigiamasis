package lt.vcs.baigiamasis.mainmenu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.puzzle.model.Puzzle;
import lt.vcs.baigiamasis.dungeon.puzzle.model.PuzzleAnswer;
import lt.vcs.baigiamasis.dungeon.combat.model.EncounterCombat;
import lt.vcs.baigiamasis.dungeon.puzzle.model.EncounterPuzzle;
import lt.vcs.baigiamasis.dungeon.treasure.model.EncounterTreasure;
import lt.vcs.baigiamasis.enemy.model.Enemy;
import lt.vcs.baigiamasis.enemy.model.GiantRat;
import lt.vcs.baigiamasis.enemy.model.Goblin;
import lt.vcs.baigiamasis.enemy.model.Skeleton;
import lt.vcs.baigiamasis.enemy.model.Slime;
import lt.vcs.baigiamasis.inventory.model.Item;
import lt.vcs.baigiamasis.repository.EncounterDao;
import lt.vcs.baigiamasis.repository.EnemyDao;
import lt.vcs.baigiamasis.repository.ItemDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.repository.PuzzleAnswerDao;
import lt.vcs.baigiamasis.repository.PuzzleDao;

public class MainActivity extends AppCompatActivity {

    public static Random random = new Random();

    MaterialButton materialButton;
    MainDatabase mainDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainDatabase = MainDatabase.getInstance(getApplicationContext());

        setUpPlayButton();
        setUpItems();
        setUpEnemies();
        setUpEncounters();
        setUpPuzzles();
        setUpPuzzleAnswers();
    }


    private void setUpPlayButton(){
        materialButton = findViewById(R.id.materialButtonMainPlay);
        materialButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PlayerSelectScreenActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setUpItems(){
        Item dagger = new Item(1, "Dagger", Constant.WEAPON, 4, 0, 0);
        Item clothShirt = new Item(2, "Cloth shirt", Constant.ARMOR, 0, 1, 0);
        Item shortSword = new Item(3, "Shortsword", Constant.WEAPON, 6, 0, 25);
        Item leatherTunic = new Item(4, "Leather tunic", Constant.ARMOR, 0, 3, 25);
        Item longsword = new Item(5, "Longsword",Constant.WEAPON, 8, 0, 50);
        Item chainVest = new Item(6, "Chain vest", Constant.ARMOR, 0, 5, 50);

        ItemDao itemDao = mainDatabase.itemDao();

        itemDao.insertItem(dagger);
        itemDao.insertItem(clothShirt);
        itemDao.insertItem(shortSword);
        itemDao.insertItem(leatherTunic);
        itemDao.insertItem(longsword);
        itemDao.insertItem(chainVest);
    }

    private void setUpEnemies(){
        Enemy goblin = new Goblin(1);
        Enemy skeleton = new Skeleton(2);
        Enemy giantRat = new GiantRat(3);
        Enemy slime = new Slime(4);

        EnemyDao enemyDao = mainDatabase.enemyDao();

        enemyDao.insertEnemy(goblin);
        enemyDao.insertEnemy(giantRat);
        enemyDao.insertEnemy(slime);
        enemyDao.insertEnemy(skeleton);
    }

    private void setUpEncounters(){
        EncounterDao encounterDao = mainDatabase.encounterDao();

        encounterDao.insertItem(new EncounterCombat(1, 1));
        encounterDao.insertItem(new EncounterCombat(2, 2));
        encounterDao.insertItem(new EncounterCombat(3, 3));
        encounterDao.insertItem(new EncounterCombat(4, 4));
        encounterDao.insertItem(new EncounterPuzzle(5));
        encounterDao.insertItem(new EncounterPuzzle(6));
        encounterDao.insertItem(new EncounterTreasure(7, 15));
        encounterDao.insertItem(new EncounterTreasure(8, 8));
    }

    private void setUpPuzzles(){
        PuzzleDao puzzleDao = mainDatabase.puzzleDao();

        puzzleDao.insertItem(new Puzzle(1, "What has one eye, but can’t see?"));
        puzzleDao.insertItem(new Puzzle(2, "What has many needles, but doesn’t sew?"));
        puzzleDao.insertItem(new Puzzle(3, "What has hands, but can’t clap?"));
        puzzleDao.insertItem(new Puzzle(4, "What has legs, but doesn’t walk?"));
        puzzleDao.insertItem(new Puzzle(5, "What has one head, one foot and four legs?"));
        puzzleDao.insertItem(new Puzzle(6, "What can you catch, but not throw?"));
        puzzleDao.insertItem(new Puzzle(7, "What kind of band never plays music?"));
        puzzleDao.insertItem(new Puzzle(8, "What has many teeth, but can’t bite?"));
        puzzleDao.insertItem(new Puzzle(9, "What is cut on a table, but is never eaten?"));
        puzzleDao.insertItem(new Puzzle(10, "What has words, but never speaks?"));
    }

    private void setUpPuzzleAnswers(){
        PuzzleAnswerDao puzzleAnswerDao = mainDatabase.puzzleAnswerDao();

        puzzleAnswerDao.insertItem(new PuzzleAnswer(1, "A needle", false));
        puzzleAnswerDao.insertItem(new PuzzleAnswer(2, "A pine tree", false));
        puzzleAnswerDao.insertItem(new PuzzleAnswer(3, "A clock", false));
        puzzleAnswerDao.insertItem(new PuzzleAnswer(4, "A table", false));
        puzzleAnswerDao.insertItem(new PuzzleAnswer(5, "A bed", false));
        puzzleAnswerDao.insertItem(new PuzzleAnswer(6, "A cold", false));
        puzzleAnswerDao.insertItem(new PuzzleAnswer(7, "A rubber", false));
        puzzleAnswerDao.insertItem(new PuzzleAnswer(8, "A comb", false));
        puzzleAnswerDao.insertItem(new PuzzleAnswer(9, "A deck of cards", false));
        puzzleAnswerDao.insertItem(new PuzzleAnswer(10, "A book", false));
    }
}