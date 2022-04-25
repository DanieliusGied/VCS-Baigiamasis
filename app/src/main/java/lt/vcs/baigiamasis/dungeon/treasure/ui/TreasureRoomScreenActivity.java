package lt.vcs.baigiamasis.dungeon.treasure.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.dungeon.model.Dungeon;
import lt.vcs.baigiamasis.dungeon.model.Encounter;
import lt.vcs.baigiamasis.dungeon.ui.ExploreDungeonScreenActivity;
import lt.vcs.baigiamasis.inventory.model.Inventory;
import lt.vcs.baigiamasis.inventory.model.Item;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.repository.DungeonDao;
import lt.vcs.baigiamasis.repository.EncounterDao;
import lt.vcs.baigiamasis.repository.InventoryDao;
import lt.vcs.baigiamasis.repository.ItemDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.repository.PlayerDao;

public class TreasureRoomScreenActivity extends AppCompatActivity {

    private MaterialButton materialButtonClaim;

    private PlayerDao playerDao;
    private DungeonDao dungeonDao;
    private InventoryDao inventoryDao;
    private ItemDao itemDao;

    private Player player;
    private Dungeon dungeon;
    private Encounter encounter;

    private Item rewardItem;
    private int randomId;
    private int healthReward;
    private int manaReward;

    private Resources resources;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDatabase();
        setUpUI();
    }


    //SET-UP DATABASE
    private void setUpDatabase() {
        Intent intent = getIntent();
        int characterID = intent.getIntExtra(Constant.PLAYER, 0);
        int encounterID = intent.getIntExtra(Constant.RANDOM_DUNGEON, 0);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        playerDao = mainDatabase.playerDao();
        dungeonDao = mainDatabase.dungeonDao();
        EncounterDao encounterDao = mainDatabase.encounterDao();
        inventoryDao = mainDatabase.inventoryDao();
        itemDao = mainDatabase.itemDao();

        player = playerDao.getItem(characterID);
        encounter = encounterDao.getItem(encounterID);
        dungeon = dungeonDao.getItemFromCharacter(player.getId());

        resources = getResources();
        random = new Random();
    }

    //SET-UP UI
    private void setUpUI(){
        setContentView(R.layout.activity_treasure_room_screen);

        randomId = random.nextInt(10)+1;
        if (encounter.getId() == 7) setUpItemReward();
        if (encounter.getId() == 8) {
            healthReward = random.nextInt(8)+1;
            manaReward = random.nextInt(8)+1;
        }

        setUpClaimButton();
        setUpProceedButton();
        setUpText();
    }

    private void setUpText(){
        TextView textView = findViewById(R.id.textViewTreasureRoom);

        if (encounter.getId() == 7) {
            textView.setText(String.format(resources.getString(R.string.treasure_screen_item_text), rewardItem.getName(), player.getName()));
            materialButtonClaim.setText(String.format(resources.getString(R.string.treasure_screen_claim_item), rewardItem.getName()));
        } else if (encounter.getId() == 8) {
            textView.setText(String.format(resources.getString(R.string.treasure_screen_healing_text), player.getName()));
            materialButtonClaim.setText(R.string.treasure_screen_claim_healing);
        }
    }

    private void showSnackBarClaimItem(){
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayoutTreasureRoom);
        Snackbar
                .make(coordinatorLayout, String.format(resources.getString(R.string.treasure_screen_item_claimed), rewardItem.getName(), player.getName()), Snackbar.LENGTH_LONG)
                .show();
    }

    private void showSnackBarClaimHealing(){
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayoutTreasureRoom);
        Snackbar
                .make(coordinatorLayout, String.format(resources.getString(R.string.treasure_screen_healing_claimed), player.getName(), healthReward, manaReward), Snackbar.LENGTH_LONG)
                .show();
    }


    //SET-UP BUTTONS
    private void setUpClaimButton(){
        materialButtonClaim = findViewById(R.id.materialButtonTreasureRoomClaim);
        materialButtonClaim.setOnClickListener(view -> {
            if (encounter.getId() == 7){
                giveItemToPlayer();
                showSnackBarClaimItem();
                materialButtonClaim.setEnabled(false);
            } else if (encounter.getId() == 8){
                player.setCurrentHealth(player.getCurrentHealth() + healthReward);
                if (player.getCurrentHealth() > player.getMaxHealth()) player.setCurrentHealth(player.getMaxHealth());
                player.setCurrentMana(player.getCurrentMana() + manaReward);
                if (player.getCurrentMana() > player.getMaxMana()) player.setCurrentMana(player.getMaxMana());

                showSnackBarClaimHealing();
                materialButtonClaim.setEnabled(false);
            }
        });
    }

    private void setUpProceedButton(){
        MaterialButton materialButtonProceed = findViewById(R.id.materialButtonTreasureRoomProceed);
        materialButtonProceed.setText(R.string.treasure_screen_proceed);
        materialButtonProceed.setOnClickListener(view -> {
            playerDao.insertItem(player);

            dungeon.setDungeonProgress(dungeon.getDungeonProgress() + 1);
            dungeonDao.insertItem(dungeon);

            Intent intent = new Intent(TreasureRoomScreenActivity.this, ExploreDungeonScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());

            startActivity(intent);
            finish();
        });
    }

    //SET-UP REWARD CLAIM FUNCTIONALITY
    private void setUpItemReward(){
        if (randomId <= 4) {
            rewardItem = itemDao.getItem(3);
        } else if (randomId <= 8) {
            rewardItem = itemDao.getItem(4);
        } else if (randomId == 9) {
            rewardItem = itemDao.getItem(5);
        } else if (randomId == 10){
            rewardItem = itemDao.getItem(6);
        }
    }

    private void giveItemToPlayer(){
        Inventory inventory = new Inventory(false, player.getId(), rewardItem.getId());
        inventoryDao.insertItem(inventory);
    }




}