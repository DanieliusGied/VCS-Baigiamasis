package lt.vcs.baigiamasis;

import static lt.vcs.baigiamasis.Constant.PLAYER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.player.ui.PlayerInfoScreenActivity;
import lt.vcs.baigiamasis.dungeon.ui.ExploreDungeonScreenActivity;
import lt.vcs.baigiamasis.inventory.ui.InventoryScreenActivity;
import lt.vcs.baigiamasis.repository.PlayerDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class MainGameMenuScreenActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButtonPlayerInfo;
    FloatingActionButton floatingActionButtonInventory;
    PlayerDao playerDao;
    Player player;
    MaterialButton materialButtonExplore;

    private int characterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_menu);

        Intent intent = getIntent();
        characterID = intent.getIntExtra(PLAYER, 0);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        playerDao = mainDatabase.playerDao();

        player = playerDao.getItem(characterID);

        setUpCharacterInfoScreenButton();
        setUpInventoryScreenButton();
        setUpExploreDungeonButton();
    }

    private void setUpCharacterInfoScreenButton(){
        floatingActionButtonPlayerInfo = findViewById(R.id.floatingActionButtonCharacterInfoScreen);
        floatingActionButtonPlayerInfo.setOnClickListener(view -> {
            Intent intent = new Intent(MainGameMenuScreenActivity.this, PlayerInfoScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());
            startActivity(intent);
        });
    }

    private void setUpInventoryScreenButton(){
        floatingActionButtonInventory = findViewById(R.id.floatingActionButtonInventoryScreen);
        floatingActionButtonInventory.setOnClickListener(view -> {
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