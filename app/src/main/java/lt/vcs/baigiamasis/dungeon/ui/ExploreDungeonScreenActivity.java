package lt.vcs.baigiamasis.dungeon.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.*;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.puzzle.ui.PuzzleRoomScreenActivity;
import lt.vcs.baigiamasis.dungeon.treasure.ui.TreasureRoomScreenActivity;
import lt.vcs.baigiamasis.mainmenu.ui.MainGameMenuScreenActivity;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.inventory.ui.InventoryScreenActivity;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.dungeon.combat.ui.CombatScreenActivity;
import lt.vcs.baigiamasis.dungeon.model.Dungeon;
import lt.vcs.baigiamasis.dungeon.model.Encounter;
import lt.vcs.baigiamasis.player.ui.PlayerInfoScreenActivity;
import lt.vcs.baigiamasis.repository.PlayerDao;
import lt.vcs.baigiamasis.repository.DungeonDao;
import lt.vcs.baigiamasis.repository.EncounterDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class ExploreDungeonScreenActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private MaterialButton materialButtonActivate;
    private MaterialButton materialButtonSkip;
    private MaterialButton materialButtonFlee;
    private MaterialButton materialButtonPlayerInfo;
    private MaterialButton materialButtonInventory;

    private PlayerDao playerDao;
    private EncounterDao encounterDao;
    private DungeonDao dungeonDao;

    private Player player;
    private Dungeon dungeon;
    private Encounter encounter;

    private Resources resources;

    private int randomEncounterID;
    private int characterID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDatabase();
        setUpUI();


    }


    //SET-UP DATABASE:
    private void setUpDatabase() {
        Intent intent = getIntent();
        characterID = intent.getIntExtra(Constant.PLAYER, 0);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        playerDao = mainDatabase.playerDao();
        encounterDao = mainDatabase.encounterDao();
        dungeonDao = mainDatabase.dungeonDao();

        player = playerDao.getItem(characterID);
        dungeon = dungeonDao.getItemFromCharacter(player.getId());

        resources = getResources();
    }

    //SET UP UI:
    private void setUpUI(){
        setContentView(R.layout.activity_explore_dungeon_screen);

        setUpRandomEncounter();

        setUpActivateButton();
        setUpSkipButton();
        setUpFleeButton();
        setUpCharacterInfoScreenButton();
        setUpInventoryScreenButton();

        setUpImageView();
        setUpTextView();

        makeSnackbarOnSkip();
        makeSnackbarOnLevelUp();
        makeSnackbarWhenLeveledUp();
        makeSnackbarOnFlee();
    }

    private void setUpRandomEncounter() {
        Random random = new Random();
        randomEncounterID = random.nextInt(8) + 1;

        encounter = encounterDao.getItem(randomEncounterID);
    }

    private void setUpActivateButton(){
        materialButtonActivate = findViewById(R.id.materialButtonExploreScreenActivate);
        materialButtonActivate.setText(encounter.getActivateButtonText());

        materialButtonActivate.setOnClickListener(view -> {
            Intent intent;

            switch(randomEncounterID){
                case 1:
                case 2:
                case 3:
                case 4:
                    intent = new Intent(ExploreDungeonScreenActivity.this, CombatScreenActivity.class);
                    intent.putExtra(Constant.PLAYER, characterID);
                    intent.putExtra(Constant.RANDOM_DUNGEON, randomEncounterID);
                    startActivity(intent);
                    finish();
                    break;
                case 5:
                case 6:
                    intent = new Intent(ExploreDungeonScreenActivity.this, PuzzleRoomScreenActivity.class);
                    intent.putExtra(Constant.PLAYER, characterID);
                    intent.putExtra(Constant.RANDOM_DUNGEON, randomEncounterID);
                    startActivity(intent);
                    finish();
                    break;
                case 7:
                case 8:
                    intent = new Intent(ExploreDungeonScreenActivity.this, TreasureRoomScreenActivity.class);
                    intent.putExtra(Constant.PLAYER, characterID);
                    intent.putExtra(Constant.RANDOM_DUNGEON, randomEncounterID);
                    startActivity(intent);
                    finish();
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

    private void setUpFleeButton(){
        materialButtonFlee = findViewById(R.id.materialButtonExploreScreenFlee);
        materialButtonFlee.setText(R.string.explore_dungeon_screen_flee);
        materialButtonFlee.setOnClickListener(view -> {
            Intent intent = new Intent(ExploreDungeonScreenActivity.this, MainGameMenuScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());

            dungeon.setDungeonProgress(0);
            dungeon.setSkipsRemaining(2);
            dungeonDao.insertItem(dungeon);
            playerDao.insertItem(player);

            startActivity(intent);
            finish();
        });
    }

    private void setUpCharacterInfoScreenButton(){
        materialButtonPlayerInfo = findViewById(R.id.materialButtonExploreDungeonScreenPlayerInfo);
        materialButtonPlayerInfo.setOnClickListener(view -> {
            Intent intent = new Intent(ExploreDungeonScreenActivity.this, PlayerInfoScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());
            startActivity(intent);
        });
    }

    private void setUpInventoryScreenButton(){
        materialButtonInventory = findViewById(R.id.materialButtonExploreDungeonInventoryScreen);
        materialButtonInventory.setOnClickListener(view -> {
            Intent intent = new Intent(ExploreDungeonScreenActivity.this, InventoryScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());
            startActivity(intent);
        });
    }

    private void makeSnackbarOnSkip(){
        if (dungeon.isSkipped()){


            CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayoutExploreDungeonScreen);
            Snackbar
                    .make(coordinatorLayout, resources.getString(R.string.explore_dungeon_screen_encounter_skipped), Snackbar.LENGTH_LONG)
                    .show();

            dungeon.setSkipped(false);
            dungeonDao.insertItem(dungeon);
        }
    }

    private void makeSnackbarOnFlee(){
        if (dungeon.isDidFlee()){


            CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayoutExploreDungeonScreen);
            Snackbar
                    .make(coordinatorLayout, resources.getString(R.string.explore_dungeon_screen_encounter_flee), Snackbar.LENGTH_LONG)
                    .show();

            dungeon.setDidFlee(false);
            dungeonDao.insertItem(dungeon);
        }
    }

    private void makeSnackbarOnLevelUp(){
        if (player.getCurrentXP() >= player.getXpToLevel()){
            player.setLeveledUp(true);
            player.setLevelUpPoints(player.getLevelUpPoints()+3);
            player.setLevel(player.getLevel()+1);
            player.calculateXPToLevel();
            player.setCurrentXP(0);

            playerDao.insertItem(player);

            CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayoutExploreDungeonScreen);
            Snackbar
                    .make(coordinatorLayout, resources.getString(R.string.explore_dungeon_screen_level_up_available), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private void makeSnackbarWhenLeveledUp(){
        if (player.isLeveledUp()) {
            CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayoutExploreDungeonScreen);
            Snackbar
                    .make(coordinatorLayout, resources.getString(R.string.explore_dungeon_screen_level_up_available), Snackbar.LENGTH_LONG)
                    .show();
        }
    }


    // TODO: 4/22/2022 set-up images when creating the ui
    private void setUpImageView(){
        imageView = findViewById(R.id.imageViewExploreScreen);

        Random random = new Random();
        switch (random.nextInt(3)){
            case 0:
                imageView.setImageResource(R.drawable.explore_hallway);
                break;
            case 1:
                imageView.setImageResource(R.drawable.explore_hallway_2);
                break;
            case 2:
                imageView.setImageResource(R.drawable.explore_hallway_3);
                break;
            default:
                break;
        }
    }

    private void setUpTextView(){
        textView = findViewById(R.id.textViewExploreScreen);
        textView.setText(encounter.getDescriptionText());
    }


}