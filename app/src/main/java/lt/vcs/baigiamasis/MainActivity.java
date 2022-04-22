package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

import lt.vcs.baigiamasis.character.ui.CharacterSelectScreenActivity;
import lt.vcs.baigiamasis.dungeon.model.EncounterCombat;
import lt.vcs.baigiamasis.dungeon.model.EncounterPuzzle;
import lt.vcs.baigiamasis.dungeon.model.EncounterTreasure;
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
    }


    private void setUpPlayButton(){
        materialButton = findViewById(R.id.materialButtonMainPlay);
        materialButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CharacterSelectScreenActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setUpItems(){
        Item dagger = new Item(1, "Dagger", Constant.WEAPON, 4, 0, 0);
        Item clothShirt = new Item(2, "Cloth armor", Constant.ARMOR, 0, 1, 0);

        ItemDao itemDao = mainDatabase.itemDao();

        itemDao.insertItem(dagger);
        itemDao.insertItem(clothShirt);
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
}