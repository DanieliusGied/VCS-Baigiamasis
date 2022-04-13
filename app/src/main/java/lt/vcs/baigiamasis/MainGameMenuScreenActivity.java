package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.zaidimukasclasses.Character;
import lt.vcs.baigiamasis.zaidimukasclasses.Constant;

public class MainGameMenuScreenActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton1;
    FloatingActionButton floatingActionButton2;
    CharacterDao characterDao;
    Character character;

    // TODO: 4/11/2022 DELETE THE WHOLE ACTIVITY AND MOVE CHAR CREATION TO FIRST SCREEN, CHANGE THE NAME 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_menu);

        MainDatabase database = Room.databaseBuilder(
                getApplicationContext(),
                MainDatabase.class,
                "main.db"
        ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        characterDao = database.characterDao();
        character = characterDao.getItem(Constant.CHARACTER_ID);

        TextView textView = (TextView) findViewById(R.id.textViewSecondScreen);
        textView.setText("Welcome " + character.getName());

        setUpCharacterInfoScreenButton();
        setUpInventoryScreenButton();
    }

    private void setUpCharacterInfoScreenButton(){
        floatingActionButton1 = findViewById(R.id.floatingActionButtonCharacterInfoScreen);
        floatingActionButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainGameMenuScreenActivity.this, CharacterInfoScreenActivity.class);
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
                startActivity(intent);
            }
        });
    }

}