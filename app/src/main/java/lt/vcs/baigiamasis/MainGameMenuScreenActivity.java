package lt.vcs.baigiamasis;

import static lt.vcs.baigiamasis.Constant.CHARACTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import lt.vcs.baigiamasis.character.ui.CharacterInfoScreenActivity;
import lt.vcs.baigiamasis.dungeon.ui.ExploreDungeonScreenActivity;
import lt.vcs.baigiamasis.inventory.ui.InventoryScreenActivity;
import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.character.model.Character;

public class MainGameMenuScreenActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton1;
    FloatingActionButton floatingActionButton2;
    CharacterDao characterDao;
    Character character;
    MaterialButton materialButtonExplore;

    private int characterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_menu);

        Intent intent = getIntent();
        characterID = intent.getIntExtra(CHARACTER, 0);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        characterDao = mainDatabase.characterDao();

        character = characterDao.getItem(characterID);

        setUpCharacterInfoScreenButton();
        setUpInventoryScreenButton();
        setUpExploreDungeonButton();
    }

    private void setUpCharacterInfoScreenButton(){
        floatingActionButton1 = findViewById(R.id.floatingActionButtonCharacterInfoScreen);
        floatingActionButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainGameMenuScreenActivity.this, CharacterInfoScreenActivity.class);
                intent.putExtra(CHARACTER, character.getId());
                startActivity(intent);
            }
        });
    }

    private void setUpInventoryScreenButton(){
        floatingActionButton2 = findViewById(R.id.floatingActionButtonInventoryScreen);
        floatingActionButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainGameMenuScreenActivity.this, InventoryScreenActivity.class);
                intent.putExtra(CHARACTER, character.getId());
                startActivity(intent);
            }
        });
    }

    private void setUpExploreDungeonButton(){
        materialButtonExplore = findViewById(R.id.materialButtonMainGameMenuExploreDungeon);
        materialButtonExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainGameMenuScreenActivity.this, ExploreDungeonScreenActivity.class);
                intent.putExtra(CHARACTER, character.getId());
                startActivity(intent);
            }
        });
    }

}