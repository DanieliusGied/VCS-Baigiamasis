package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.zaidimukasclasses.Character;
import lt.vcs.baigiamasis.zaidimukasclasses.Constant;

public class CharacterInfoScreenActivity extends AppCompatActivity {

    MaterialButton materialButton;
    Character character;
    CharacterDao characterDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_info_screen);

        MainDatabase database = Room.databaseBuilder(
                getApplicationContext(),
                MainDatabase.class,
                "main.db"
        ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        characterDao = database.characterDao();
        character = characterDao.getItem(Constant.CHARACTER_ID);

        setUpText();
        setUpCloseButton();
    }

    private void setUpText() {
        TextView textViewName = (TextView) findViewById(R.id.textViewCharacterInfoScreenName);
        textViewName.setText("Name: " + character.getName());
        TextView textViewHP = (TextView) findViewById(R.id.textViewCharacterInfoScreenHP);
        textViewHP.setText("HP: " + character.getCurrentHealth() + " / " + character.getMaxHealth());
        TextView textViewMP = (TextView) findViewById(R.id.textViewCharacterInfoScreenMP);
        textViewMP.setText("MP: null");
        TextView textViewXP = (TextView) findViewById(R.id.textViewCharacterInfoScreenXP);
        textViewXP.setText("XP: null");
        TextView textViewSTR = (TextView) findViewById(R.id.textViewCharacterInfoScreenSTR);
        textViewSTR.setText("STR: " + character.getStatStr());
        TextView textViewCON = (TextView) findViewById(R.id.textViewCharacterInfoScreenCON);
        textViewCON.setText("CON: " + character.getStatCon());
        TextView textViewDEX = (TextView) findViewById(R.id.textViewCharacterInfoScreenDEX);
        textViewDEX.setText("DEX: " + character.getStatDex());
    }

    private void setUpCloseButton(){
        materialButton = findViewById(R.id.materialButtonCharacterInfoScreenClose);
        materialButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    };
}