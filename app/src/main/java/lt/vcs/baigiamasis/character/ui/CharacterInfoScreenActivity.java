package lt.vcs.baigiamasis.character.ui;

import static lt.vcs.baigiamasis.Constant.CHARACTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.InventoryDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.character.model.Character;

public class CharacterInfoScreenActivity extends AppCompatActivity {

    MaterialButton materialButton;
    Character character;
    CharacterDao characterDao;
    InventoryDao inventoryDao;

    private int characterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_info_screen);

        Intent intent = getIntent();
        characterID = intent.getIntExtra(CHARACTER, 0);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        characterDao = mainDatabase.characterDao();
        inventoryDao = mainDatabase.inventoryDao();

        character = characterDao.getItem(characterID);

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
        TextView textViewSTR = (TextView) findViewById(R.id.textViewCharacterInfoScreenSTRValue);
        textViewSTR.setText(character.getStatStr());
        TextView textViewCON = (TextView) findViewById(R.id.textViewCharacterInfoScreenCONValue);
        textViewCON.setText(character.getStatCon());
        TextView textViewDEX = (TextView) findViewById(R.id.textViewCharacterInfoScreenDEXValue);
        textViewDEX.setText(character.getStatDex());
        TextView textViewArmor = (TextView) findViewById(R.id.textViewCharacterInfoScreenArmorValue);
        textViewArmor.setText(inventoryDao.getArmorFromCharacter(character.getId()).getArmor() + character.getStatDex()-10);
        TextView textViewDMG = (TextView) findViewById(R.id.textViewCharacterInfoScreenDMGValue);
        textViewDMG.setText("1 - " + inventoryDao.getWeaponFromCharacter(character.getId()).getMaxDamage() + (character.getStatStr()-10));
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