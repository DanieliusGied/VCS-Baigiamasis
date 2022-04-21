package lt.vcs.baigiamasis.dungeon.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

import lt.vcs.baigiamasis.Constant;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.character.model.Character;
import lt.vcs.baigiamasis.combat.ui.CombatScreenActivity;
import lt.vcs.baigiamasis.dungeon.model.Dungeon;
import lt.vcs.baigiamasis.dungeon.model.Encounter;
import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.EncounterDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class ExploreDungeonScreenActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    MaterialButton materialButtonActivate;
    MaterialButton materialButtonSkip;
    MaterialButton materialButtonFlee;

    CharacterDao characterDao;
    EncounterDao encounterDao;

    Character character;
    Dungeon dungeon;
    Encounter encounter;

    private Random random;
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

        character = characterDao.getItem(characterID);

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
    }

    private void setUpRandomEncounter() {
        random = new Random();
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
                    break;
                case 5:
                case 6:
                    intent = new Intent(ExploreDungeonScreenActivity.this, PuzzleRoomScreenActivity.class);
                    intent.putExtra(Constant.CHARACTER, characterID);
                    intent.putExtra(Constant.RANDOM_DUNGEON, randomInt);
                    break;
                case 7:
                case 8:
                    intent = new Intent(ExploreDungeonScreenActivity.this, TreasureRoomScreenActivity.class);
                    intent.putExtra(Constant.CHARACTER, characterID);
                    intent.putExtra(Constant.RANDOM_DUNGEON, randomInt);
                    break;
                default:
                    break;
            }
        });
    }

    private void setUpSkipButton(){
        materialButtonSkip = findViewById(R.id.materialButtonExploreScreenSkip);
        materialButtonSkip.setOnClickListener(view -> {
            int skipChance = random.nextInt(2) + 1;
            switch(skipChance){
                case 1:
                    // Fail = snackbar notification that it failed, run start encounter
                case 2:
                    // Success = snackbar notification that it succeeded, rerun this page
                default:
            }
        });
    }

    private void setUpFleeButton(){
        materialButtonFlee = findViewById(R.id.materialButtonExploreScreenFlee);
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