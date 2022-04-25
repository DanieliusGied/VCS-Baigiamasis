package lt.vcs.baigiamasis.mainmenu.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.player.ui.PlayerInfoScreenActivity;
import lt.vcs.baigiamasis.dungeon.ui.ExploreDungeonScreenActivity;
import lt.vcs.baigiamasis.inventory.ui.InventoryScreenActivity;
import lt.vcs.baigiamasis.repository.PlayerDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class MainGameMenuScreenActivity extends AppCompatActivity {

    MaterialButton materialButtonPlayerInfo;
    MaterialButton materialButtonInventory;
    PlayerDao playerDao;
    Player player;
    MaterialButton materialButtonExplore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDatabase();
        setUpUI();
    }

    //SET-UP DATABASE
    private void setUpDatabase() {
        Intent intent = getIntent();
        int characterID = intent.getIntExtra(PLAYER, 0);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        playerDao = mainDatabase.playerDao();

        player = playerDao.getItem(characterID);
    }

    //SET-UP UI
    private void setUpUI(){
        setContentView(R.layout.activity_main_game_menu);
        setUpCharacterInfoScreenButton();
        setUpInventoryScreenButton();
        setUpExploreDungeonButton();
    }

    //SET-UP BUTTONS
    private void setUpCharacterInfoScreenButton(){
        materialButtonPlayerInfo = findViewById(R.id.materialButtonCharacterInfoScreen);
        materialButtonPlayerInfo.setOnClickListener(view -> {
            Intent intent = new Intent(MainGameMenuScreenActivity.this, PlayerInfoScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());
            startActivity(intent);
        });
    }

    private void setUpInventoryScreenButton(){
        materialButtonInventory = findViewById(R.id.materialButtonInventoryScreen);
        materialButtonInventory.setOnClickListener(view -> {
            Intent intent = new Intent(MainGameMenuScreenActivity.this, InventoryScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());
            startActivity(intent);
        });
    }

    private void setUpExploreDungeonButton(){
        materialButtonExplore = findViewById(R.id.materialButtonMainGameMenuExploreDungeon);
        materialButtonExplore.setOnClickListener(view -> {
            Intent intent = new Intent(MainGameMenuScreenActivity.this, ExploreDungeonScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());
            startActivity(intent);
            finish();
        });
    }

}