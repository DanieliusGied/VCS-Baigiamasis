package lt.vcs.baigiamasis.character.ui;

import static lt.vcs.baigiamasis.Constant.CHARACTER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_info_screen);

        Intent intent = getIntent();
        int characterID = intent.getIntExtra(CHARACTER, 0);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        characterDao = mainDatabase.characterDao();
        inventoryDao = mainDatabase.inventoryDao();

        character = characterDao.getItem(characterID);

        setUpText();
        setUpCloseButton();
    }

    // TODO: 4/21/2022 FIX GET ARMOR BUG IF ARMOR IS NOT EQUIPPED
    private void setUpText() {
        Resources resources = getResources();

        TextView textViewName = findViewById(R.id.textViewCharacterInfoScreenName);
        String textName = String.format(resources.getString(R.string.Character_Info_Screen_Name), character.getName());
        textViewName.setText(textName);

        TextView textViewHP = findViewById(R.id.textViewCharacterInfoScreenHP);
        String textHP = String.format(resources.getString(R.string.Character_Info_Screen_HP), character.getCurrentHealth(), character.getMaxHealth());
        textViewHP.setText(textHP);

        TextView textViewMP = findViewById(R.id.textViewCharacterInfoScreenMP);
        textViewMP.setText(R.string.Character_Info_Screen_MP);

        TextView textViewXP = findViewById(R.id.textViewCharacterInfoScreenXP);
        textViewXP.setText(R.string.Character_Info_Screen_XP);

        TextView textViewSTR = findViewById(R.id.textViewCharacterInfoScreenSTRValue);
        textViewSTR.setText(Integer.toString(character.getStatStr()));

        TextView textViewCON = findViewById(R.id.textViewCharacterInfoScreenCONValue);
        textViewCON.setText(Integer.toString(character.getStatCon()));

        TextView textViewDEX = findViewById(R.id.textViewCharacterInfoScreenDEXValue);
        textViewDEX.setText(Integer.toString(character.getStatDex()));

        TextView textViewArmor = findViewById(R.id.textViewCharacterInfoScreenArmorValue);
        int characterArmor = character.getStatDex()-10;
        if (inventoryDao.getArmorFromCharacter(character.getId()) != null) {
            characterArmor += inventoryDao.getArmorFromCharacter(character.getId()).getArmor();
        }
        String textArmor = Integer.toString(characterArmor);
        textViewArmor.setText(textArmor);

        TextView textViewDMG = findViewById(R.id.textViewCharacterInfoScreenDMGValue);
        int characterMaxDmg = character.getStatStr()-10;
        if (inventoryDao.getWeaponFromCharacter(character.getId()) != null){
            characterMaxDmg += inventoryDao.getWeaponFromCharacter(character.getId()).getMaxDamage();
        }
        String textDMG = String.format(resources.getString(R.string.Character_Info_Screen_DMG), characterMaxDmg);
        textViewDMG.setText(textDMG);
    }

    private void setUpCloseButton(){
        materialButton = findViewById(R.id.materialButtonCharacterInfoScreenClose);
        materialButton.setOnClickListener(view -> finish());
    }
}