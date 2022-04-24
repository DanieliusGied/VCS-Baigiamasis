package lt.vcs.baigiamasis.player.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.repository.PlayerDao;
import lt.vcs.baigiamasis.repository.InventoryDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class PlayerInfoScreenActivity extends AppCompatActivity {

    MaterialButton materialButton;
    Player player;
    PlayerDao playerDao;
    InventoryDao inventoryDao;

    FloatingActionButton floatingActionButtonSTR;
    FloatingActionButton floatingActionButtonDEX;
    FloatingActionButton floatingActionButtonCON;
    FloatingActionButton floatingActionButtonWIS;
    TextView textViewLevelUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int characterID = intent.getIntExtra(PLAYER, 0);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        playerDao = mainDatabase.playerDao();
        inventoryDao = mainDatabase.inventoryDao();

        player = playerDao.getItem(characterID);

        setUpUI();
    }

    private void setUpUI(){
        setContentView(R.layout.activity_player_info_screen);

        setUpText();
        setUpCloseButton();
        setUpProgressBars();

        setUpLevelUpButtonsAndText();
        checkForLevelUp();
    }

    private void setUpText() {
        Resources resources = getResources();

        TextView textViewName = findViewById(R.id.textViewPlayerInfoScreenName);
        String textName = String.format(resources.getString(R.string.player_info_screen_name), player.getName(), player.getLevel());
        textViewName.setText(textName);

        TextView textViewHP = findViewById(R.id.textViewPlayerInfoScreenHP);
        String textHP = String.format(resources.getString(R.string.player_info_screen_hp), player.getCurrentHealth(), player.getMaxHealth());
        textViewHP.setText(textHP);

        TextView textViewMP = findViewById(R.id.textViewPlayerInfoScreenMP);
        String textMP = String.format(resources.getString(R.string.player_info_screen_mp), player.getCurrentMana(), player.getMaxMana());
        textViewMP.setText(textMP);

        TextView textViewXP = findViewById(R.id.textViewPlayerInfoScreenXP);
        String textXP = String.format(resources.getString(R.string.player_info_screen_xp), player.getCurrentXP(), player.getXpToLevel());
        textViewXP.setText(textXP);

        TextView textViewSTR = findViewById(R.id.textViewPlayerInfoScreenSTRValue);
        textViewSTR.setText(String.format(resources.getString(R.string.player_info_screen_stat), player.getStatStr()));

        TextView textViewCON = findViewById(R.id.textViewPlayerInfoScreenCONValue);
        textViewCON.setText(String.format(resources.getString(R.string.player_info_screen_stat), player.getStatCon()));

        TextView textViewDEX = findViewById(R.id.textViewPlayerInfoScreenDEXValue);
        textViewDEX.setText(String.format(resources.getString(R.string.player_info_screen_stat), player.getStatDex()));

        TextView textViewWIS = findViewById(R.id.textViewPlayerInfoScreenWISValue);
        textViewWIS.setText(String.format(resources.getString(R.string.player_info_screen_stat), player.getStatWis()));

        TextView textViewArmor = findViewById(R.id.textViewPlayerInfoScreenArmorValue);
        int characterArmor = player.getArmor();
        if (inventoryDao.getArmorFromCharacter(player.getId()) != null) {
            characterArmor += inventoryDao.getArmorFromCharacter(player.getId()).getArmor();
        }
        String textArmor = Integer.toString(characterArmor);
        textViewArmor.setText(textArmor);

        TextView textViewDMG = findViewById(R.id.textViewPlayerInfoScreenDMGValue);
        int characterMaxDmg = player.getStatStr()-10 + Constant.BASE_DAMAGE;
        if (inventoryDao.getWeaponFromCharacter(player.getId()) != null){
            characterMaxDmg += inventoryDao.getWeaponFromCharacter(player.getId()).getMaxDamage();
        }
        String textDMG = String.format(resources.getString(R.string.player_info_screen_dmg), characterMaxDmg);
        textViewDMG.setText(textDMG);
    }

    private void setUpLevelUpButtonsAndText(){
        floatingActionButtonSTR = findViewById(R.id.floatingActionButtonPlayerInfoScreenSTRIncrease);
        floatingActionButtonDEX = findViewById(R.id.floatingActionButtonPlayerInfoScreenDEXIncrease);
        floatingActionButtonCON = findViewById(R.id.floatingActionButtonPlayerInfoScreenCONIncrease);
        floatingActionButtonWIS = findViewById(R.id.floatingActionButtonPlayerInfoScreenWISIncrease);

        Resources resources = getResources();

        textViewLevelUp = findViewById(R.id.textViewPlayerInfoScreenLevelUpPts);
        textViewLevelUp.setText(String.format(resources.getString(R.string.player_info_screen_level_up_points), player.getLevelUpPoints()));
        textViewLevelUp.setEnabled(false);
        textViewLevelUp.setVisibility(View.INVISIBLE);

        floatingActionButtonSTR.setOnClickListener(view -> {
            player.setStatStr(player.getStatStr()+1);
            player.setLevelUpPoints(player.getLevelUpPoints()-1);
            player.levelUp();
            playerDao.insertItem(player);
            setUpUI();
        });
        floatingActionButtonSTR.setEnabled(false);
        floatingActionButtonSTR.setVisibility(View.INVISIBLE);

        floatingActionButtonDEX.setOnClickListener(view -> {
            player.setStatDex(player.getStatDex()+1);
            player.setLevelUpPoints(player.getLevelUpPoints()-1);
            player.levelUp();
            playerDao.insertItem(player);
            setUpUI();
        });
        floatingActionButtonDEX.setEnabled(false);
        floatingActionButtonDEX.setVisibility(View.INVISIBLE);

        floatingActionButtonCON.setOnClickListener(view -> {
            player.setStatCon(player.getStatCon()+1);
            player.setLevelUpPoints(player.getLevelUpPoints()-1);
            player.levelUp();
            playerDao.insertItem(player);
            setUpUI();
        });
        floatingActionButtonCON.setEnabled(false);
        floatingActionButtonCON.setVisibility(View.INVISIBLE);

        floatingActionButtonWIS.setOnClickListener(view -> {
            player.setStatWis(player.getStatWis()+1);
            player.setLevelUpPoints(player.getLevelUpPoints()-1);
            player.levelUp();
            playerDao.insertItem(player);
            setUpUI();
        });
        floatingActionButtonWIS.setEnabled(false);
        floatingActionButtonWIS.setVisibility(View.INVISIBLE);
    }

    private void checkForLevelUp(){
        if (player.isLeveledUp()) {
            textViewLevelUp.setEnabled(true);
            textViewLevelUp.setVisibility(View.VISIBLE);
            floatingActionButtonSTR.setEnabled(true);
            floatingActionButtonSTR.setVisibility(View.VISIBLE);
            floatingActionButtonDEX.setEnabled(true);
            floatingActionButtonDEX.setVisibility(View.VISIBLE);
            floatingActionButtonCON.setEnabled(true);
            floatingActionButtonCON.setVisibility(View.VISIBLE);
            floatingActionButtonWIS.setEnabled(true);
            floatingActionButtonWIS.setVisibility(View.VISIBLE);
        }
        if (!player.isLeveledUp()) {
            textViewLevelUp.setEnabled(false);
            textViewLevelUp.setVisibility(View.INVISIBLE);
            floatingActionButtonSTR.setEnabled(false);
            floatingActionButtonSTR.setVisibility(View.INVISIBLE);
            floatingActionButtonDEX.setEnabled(false);
            floatingActionButtonDEX.setVisibility(View.INVISIBLE);
            floatingActionButtonCON.setEnabled(false);
            floatingActionButtonCON.setVisibility(View.INVISIBLE);
            floatingActionButtonWIS.setEnabled(false);
            floatingActionButtonWIS.setVisibility(View.INVISIBLE);
        }
    }

    private void setUpCloseButton(){
        materialButton = findViewById(R.id.materialButtonPlayerInfoScreenClose);
        materialButton.setOnClickListener(view -> finish());
    }

    private void setUpProgressBars(){
        ProgressBar progressBarHP = findViewById(R.id.progressBarPlayerInfoScreenHP);
        ProgressBar progressBarMP = findViewById(R.id.progressBarPlayerInfoScreenMP);
        ProgressBar progressBarXP = findViewById(R.id.progressBarPlayerInfoScreenXP);

        int progressHP = (int) Math.round((double) player.getCurrentHealth() / (double) player.getMaxHealth() * 100);
        progressBarHP.setProgress(progressHP);

        int progressMP = (int) Math.round((double) player.getCurrentMana() / (double) player.getMaxMana() * 100);
        progressBarMP.setProgress(progressMP);

        int progressXP = (int) Math.round((double) player.getCurrentXP() / (double) player.getXpToLevel() * 100);
        progressBarXP.setProgress(progressXP);
    }
}