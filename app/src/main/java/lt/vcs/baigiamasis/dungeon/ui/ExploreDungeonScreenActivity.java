package lt.vcs.baigiamasis.dungeon.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

import lt.vcs.baigiamasis.Constant;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.character.model.Character;
import lt.vcs.baigiamasis.combat.ui.CombatScreenActivity;
import lt.vcs.baigiamasis.dungeon.model.Dungeon;
import lt.vcs.baigiamasis.dungeon.model.Encounter;
import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.DungeonDao;
import lt.vcs.baigiamasis.repository.EncounterDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class ExploreDungeonScreenActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private MaterialButton materialButtonActivate;
    private MaterialButton materialButtonSkip;
    private MaterialButton materialButtonFlee;

    private CharacterDao characterDao;
    private EncounterDao encounterDao;
    private DungeonDao dungeonDao;

    private Character character;
    private Dungeon dungeon;
    private Encounter encounter;

    private Resources resources;

    private int randomInt;
    private int characterID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        characterID = intent.getIntExtra(Constant.CHARACTER, 0);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        characterDao = mainDatabase.characterDao();
        encounterDao = mainDatabase.encounterDao();
        dungeonDao = mainDatabase.dungeonDao();

        character = characterDao.getItem(characterID);
        dungeon = dungeonDao.getItemFromCharacter(character.getId());

        resources = getResources();

        setUpUI();


    }

    //SET UP UI:
    private void setUpUI(){
        setContentView(R.layout.activity_explore_dungeon_screen);

        setUpRandomEncounter();

        setUpActivateButton();
        setUpSkipButton();
        setUpFleeButton();

        setUpImageView();
        setUpTextView();

        makeSnackbarOnSkip();
    }

    private void setUpRandomEncounter() {
        Random random = new Random();
        randomInt = random.nextInt(8) + 1;

        encounter = encounterDao.getItem(randomInt);
    }

    private void setUpActivateButton(){
        materialButtonActivate = findViewById(R.id.materialButtonExploreScreenActivate);
        materialButtonActivate.setText(encounter.getActivateButtonText());

        materialButtonActivate.setOnClickListener(view -> {
            Intent intent;

            switch(randomInt){
                case 1:
                case 2:
                case 3:
                case 4:
                    intent = new Intent(ExploreDungeonScreenActivity.this, CombatScreenActivity.class);
                    intent.putExtra(Constant.CHARACTER, characterID);
                    intent.putExtra(Constant.RANDOM_DUNGEON, randomInt);
                    startActivity(intent);
                    break;
                case 5:
                case 6:
                    intent = new Intent(ExploreDungeonScreenActivity.this, PuzzleRoomScreenActivity.class);
                    intent.putExtra(Constant.CHARACTER, characterID);
                    intent.putExtra(Constant.RANDOM_DUNGEON, randomInt);
                    startActivity(intent);
                    break;
                case 7:
                case 8:
                    intent = new Intent(ExploreDungeonScreenActivity.this, TreasureRoomScreenActivity.class);
                    intent.putExtra(Constant.CHARACTER, characterID);
                    intent.putExtra(Constant.RANDOM_DUNGEON, randomInt);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        });
    }

    private void setUpSkipButton(){
        materialButtonSkip = findViewById(R.id.materialButtonExploreScreenSkip);

        String skipText = String.format(resources.getString(R.string.explore_dungeon_screen_skips_remaining), dungeon.getSkipsRemaining());
        materialButtonSkip.setText(skipText);

        materialButtonSkip.setOnClickListener(view -> {
            if (dungeon.getSkipsRemaining() > 0) {
                dungeon.setSkipsRemaining(dungeon.getSkipsRemaining() - 1);
                dungeon.setSkipped(true);
                dungeonDao.insertItem(dungeon);

                recreate();
            }

            CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayoutExploreDungeonScreen);
            Snackbar
                    .make(coordinatorLayout, R.string.explore_dungeon_screen_no_skips_remaining, Snackbar.LENGTH_LONG)
                    .show();
        });
    }

    private void makeSnackbarOnSkip(){
        if (dungeon.isSkipped()){


            CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayoutExploreDungeonScreen);
            Snackbar
                    .make(coordinatorLayout, resources.getString(R.string.explore_dungeon_screen_encounter_skipped), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private void setUpFleeButton(){
        materialButtonFlee = findViewById(R.id.materialButtonExploreScreenFlee);
        materialButtonFlee.setText(R.string.explore_dungeon_screen_flee);
        materialButtonFlee.setOnClickListener(view -> {

        });
    }

    private void setUpImageView(){
        imageView = findViewById(R.id.imageViewExploreScreen);
        imageView.setImageResource(R.drawable.ic_baseline_account_circle_24); //insert image here
    }

    private void setUpTextView(){
        textView = findViewById(R.id.textViewExploreScreen);
        textView.setText(encounter.getDescriptionText());
    }


}