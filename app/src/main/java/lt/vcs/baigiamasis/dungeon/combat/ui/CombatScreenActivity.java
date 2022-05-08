package lt.vcs.baigiamasis.dungeon.combat.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.inventory.ui.InventoryScreenActivity;
import lt.vcs.baigiamasis.mainmenu.ui.MainActivity;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.dungeon.combat.model.Graveyard;
import lt.vcs.baigiamasis.dungeon.ui.ExploreDungeonScreenActivity;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.dungeon.combat.model.Combat;
import lt.vcs.baigiamasis.dungeon.model.Dungeon;
import lt.vcs.baigiamasis.dungeon.model.Encounter;
import lt.vcs.baigiamasis.enemy.model.Enemy;
import lt.vcs.baigiamasis.repository.GraveyardDao;
import lt.vcs.baigiamasis.repository.InventoryDao;
import lt.vcs.baigiamasis.repository.PlayerDao;
import lt.vcs.baigiamasis.repository.DungeonDao;
import lt.vcs.baigiamasis.repository.EncounterDao;
import lt.vcs.baigiamasis.repository.EnemyDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class CombatScreenActivity extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    private List<String> combatText;

    private MainDatabase mainDatabase;
    private PlayerDao playerDao;
    private DungeonDao dungeonDao;
    private InventoryDao inventoryDao;

    private Enemy enemy;
    private Player player;
    private Dungeon dungeon;
    private Encounter encounter;

    private Combat combat;

    private int enemyMaxHP;
    private int playerDamage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDatabase();
        setUpCombatInfo();
        setUpUI();

        showErrorDialogEncounter();
    }

    //SET-UP DATABASE
    private void setUpDatabase() {
        Intent intent = getIntent();
        int characterID = intent.getIntExtra(Constant.PLAYER, 0);
        int encounterID = intent.getIntExtra(Constant.RANDOM_DUNGEON, 0);

        mainDatabase = MainDatabase.getInstance(getApplicationContext());
        EnemyDao enemyDao = mainDatabase.enemyDao();
        playerDao = mainDatabase.playerDao();
        dungeonDao = mainDatabase.dungeonDao();
        EncounterDao encounterDao = mainDatabase.encounterDao();
        inventoryDao = mainDatabase.inventoryDao();

        player = playerDao.getItem(characterID);
        encounter = encounterDao.getItem(encounterID);
        enemy = enemyDao.getItem(encounter.getLinkedEnemyId());
        dungeon = dungeonDao.getItemFromCharacter(player.getId());
    }

    //SET-UP UI
    private void setUpUI(){
        setContentView(R.layout.activity_combat_screen);
        setUpCombatTextLog();

        setUpMaterialButtonAttack();
        setUpMaterialButtonSpell();
        setUpMaterialButtonFlee();

        setUpPortraits();

        setUpEnemy();
        setUpPlayer();
    }

    private void setUpPortraits(){
        ImageView imageViewPlayer = findViewById(R.id.imageViewCombatScreenPlayerPortrait);
        imageViewPlayer.setImageResource(R.drawable.player_portrait);

        ImageView imageViewEnemy = findViewById(R.id.imageViewCombatScreenEnemyPortrait);
        switch (enemy.getId()){
            case 1:
                imageViewEnemy.setImageResource(R.drawable.goblin_portrait);
                break;
            case 2:
                imageViewEnemy.setImageResource(R.drawable.skeleton_portrait);
                break;
            case 3:
                imageViewEnemy.setImageResource(R.drawable.rat_portrait);
                break;
            case 4:
                imageViewEnemy.setImageResource(R.drawable.slime_portrait);
                break;
            default:
                break;
        }


    }

    private void setUpEnemy(){
        TextView textViewEnemyName = findViewById(R.id.textViewCombatScreenEnemyName);
        textViewEnemyName.setText(String.format(
                getResources().getString(R.string.combat_screen_name),
                enemy.getName()
        ));

        TextView textViewEnemyHP = findViewById(R.id.textViewCombatScreenEnemyHP);
        textViewEnemyHP.setText(String.format(
                getResources().getString(R.string.combat_screen_hp),
                enemy.getHealth(),
                enemyMaxHP
        ));

        setUpProgressBars();
    }

    private void setUpPlayer(){
        TextView textViewPlayerName = findViewById(R.id.textViewCombatScreenPlayerName);
        textViewPlayerName.setText(String.format(
                getResources().getString(R.string.combat_screen_name),
                player.getName()
        ));

        TextView textViewPlayerHP = findViewById(R.id.textViewCombatScreenPlayerHP);
        textViewPlayerHP.setText(String.format(
                getResources().getString(R.string.combat_screen_hp),
                player.getCurrentHealth(),
                player.getMaxHealth()
        ));

        TextView textViewPlayerMP = findViewById(R.id.textViewCombatScreenPlayerMP);
        textViewPlayerMP.setText(String.format(
                getResources().getString(R.string.combat_screen_mp),
                player.getCurrentMana(),
                player.getMaxMana()
        ));

        setUpProgressBars();
    }

    private void setUpProgressBars(){
        ProgressBar progressBarEnemyHP = findViewById(R.id.progressBarCombatScreenEnemyHP);
        ProgressBar progressBarPlayerHP = findViewById(R.id.progressBarCombatScreenPlayerHP);
        ProgressBar progressBarPlayerMP = findViewById(R.id.progressBarCombatScreenPlayerMP);

        int progressEnemyHP = (int) Math.round((double) enemy.getHealth() / (double) enemyMaxHP * 100);
        progressBarEnemyHP.setProgress(progressEnemyHP);

        int progressPlayerHP = (int) Math.round((double) player.getCurrentHealth() / (double) player.getMaxHealth() * 100);
        progressBarPlayerHP.setProgress(progressPlayerHP);

        int progressPlayerMP = (int) Math.round((double) player.getCurrentMana() / (double) player.getMaxMana() * 100);
        progressBarPlayerMP.setProgress(progressPlayerMP);
    }

    //SET-UP BUTTON

    private void setUpMaterialButtonAttack(){
        MaterialButton materialButtonAttack = findViewById(R.id.materialButtonCombatScreenAttack);
        materialButtonAttack.setOnClickListener(view -> {
            playerAttack();
            if (enemy.getHealth() > 0) enemyAttack();
        });
    }

    private void setUpMaterialButtonSpell(){
        MaterialButton materialButtonSpell = findViewById(R.id.materialButtonCombatScreenSpell);
        materialButtonSpell.setOnClickListener(view -> {
            if (player.getCurrentMana() > 0){
                playerSpell();
                player.setCurrentMana(player.getCurrentMana() - 1);
                setUpPlayer();
                if (enemy.getHealth() > 0) enemyAttack();
            }
            if (player.getCurrentMana() == 0) {
                makeSnackbarOnOutOfMana();
            }
        });
    }

    private void setUpMaterialButtonFlee(){
        MaterialButton materialButtonFlee = findViewById(R.id.materialButtonCombatScreenFlee);
        materialButtonFlee.setOnClickListener(view -> {
            Intent intent = new Intent(CombatScreenActivity.this, ExploreDungeonScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());

            dungeon.setDungeonProgress(0);
            dungeon.setDidFlee(true);

            dungeonDao.insertItem(dungeon);
            playerDao.insertItem(player);

            startActivity(intent);
            finish();
        });
    }


    //SET-UP COMBAT
    private void setUpCombatInfo() {
        enemyMaxHP = enemy.getHealth();

        int playerWeaponDamage = 0;
        if (inventoryDao.getWeaponFromCharacter(player.getId()) != null){
            playerWeaponDamage += inventoryDao.getWeaponFromCharacter(player.getId()).getMaxDamage();
        }

        int playerArmor = player.getArmor();
        if (inventoryDao.getArmorFromCharacter(player.getId()) != null) {
            playerArmor += inventoryDao.getArmorFromCharacter(player.getId()).getArmor();
        }

        combat = new Combat(player, enemy, playerWeaponDamage, playerArmor);
    }

    private void playerAttack(){
        playerDamage = combat.calculatePlayerDamage();
        generateCombatLogPlayerAttack(playerDamage);
        enemy.setHealth(enemy.getHealth() - playerDamage);
        if (enemy.getHealth() <= 0) {
            enemy.setHealth(0);
            setUpEnemy();
            showErrorDialogEnemyDeath();
        }
        setUpEnemy();
    }

    private void enemyAttack(){
        int enemyDamage = combat.calculateEnemyDamage();
        generateCombatLogEnemyAttack(enemyDamage);
        player.setCurrentHealth(player.getCurrentHealth() - enemyDamage);
        if (player.getCurrentHealth() <= 0) {
            player.setCurrentHealth(0);
            setUpPlayer();
            showErrorDialogPlayerDeath();
        }
        setUpPlayer();
    }

    private void playerSpell(){
        playerDamage = combat.calculatePlayerDamageSpecialAttack();
        generateCombatLogPlayerSpell(playerDamage);
        enemy.setHealth(enemy.getHealth() - playerDamage);
        if (enemy.getHealth() <= 0) {
            enemy.setHealth(0);
            setUpEnemy();
            showErrorDialogEnemyDeath();
        }
        setUpEnemy();
    }

    private void makeSnackbarOnOutOfMana(){
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayoutCombatScreen);
        Snackbar
                .make(coordinatorLayout, getResources().getString(R.string.combat_screen_oom), Snackbar.LENGTH_SHORT)
                .show();
    }

    //SET-UP COMBAT LOG TEXT
    private void setUpCombatTextLog(){
        combatText = new ArrayList();

        ListView listView = findViewById(R.id.listViewCombatScreenText);

        arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                combatText){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView item = (TextView) super.getView(position,convertView,parent);

                item.setTypeface(getResources().getFont(R.font.vecna_bold_italic));
                item.setTextColor(getResources().getColor(R.color.background_brown));
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

                return item;
            }
        };

        listView.setAdapter(arrayAdapter);
    }

    private void generateCombatLogPlayerAttack(int playerDamage){
        String log = "";

        if (playerDamage == 0) log = String.format(getResources().getString(
                R.string.combat_screen_attack_missed),
                player.getName()
        );
        if (playerDamage > 0) log = String.format(getResources().getString(
                R.string.combat_screen_attack_hit),
                player.getName(),
                playerDamage
        );

        combatText.add(0, log);
        arrayAdapter.notifyDataSetChanged();
    }

    private void generateCombatLogPlayerSpell(int playerDamage){
        String log = "";

        if (playerDamage == 0) log = String.format(getResources().getString(
                R.string.combat_screen_spell_missed),
                player.getName()
        );
        if (playerDamage > 0) log = String.format(getResources().getString(
                R.string.combat_screen_spell_hit),
                player.getName(),
                playerDamage
        );

        combatText.add(0, log);
        arrayAdapter.notifyDataSetChanged();
    }

    private void generateCombatLogEnemyAttack(int enemyDamage){
        String log = "";

        if (enemyDamage == 0) log = String.format(getResources().getString(
                R.string.combat_screen_attack_missed),
                enemy.getName()
        );
        if (enemyDamage > 0) log = String.format(getResources().getString(
                R.string.combat_screen_attack_hit),
                enemy.getName(),
                enemyDamage
        );

        combatText.add(0, log);
        combatText.add(getResources().getString(
                R.string.combat_screen_turn_break)
        );
        arrayAdapter.notifyDataSetChanged();
    }

    //SET-UP DIALOGS
    private void showErrorDialogEncounter(){
        final Dialog dialog = new Dialog(CombatScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        switch (encounter.getId()){
            case 1:
                textView.setText(String.format(
                        getResources().getString(R.string.explore_dungeon_screen_goblin_encounter),
                        player.getName()
                ));
                break;
            case 2:
                textView.setText(R.string.explore_dungeon_screen_skeleton_encounter);
                break;
            case 3:
                textView.setText(R.string.explore_dungeon_screen_rat_encounter);
                break;
            case 4:
                textView.setText(R.string.explore_dungeon_screen_slime_encounter);
                break;
            default:
                break;
        }

        buttonPositive.setText(R.string.combat_screen_fight);
        buttonPositive.setOnClickListener(view -> dialog.dismiss());

        buttonNegative.setText(R.string.combat_screen_flee);
        buttonNegative.setOnClickListener(view -> {
            Intent intent = new Intent(CombatScreenActivity.this, ExploreDungeonScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());

            dungeon.setDungeonProgress(0);
            dungeon.setDidFlee(true);

            dungeonDao.insertItem(dungeon);
            playerDao.insertItem(player);

            startActivity(intent);
            finish();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showErrorDialogEnemyDeath(){
        final Dialog dialog = new Dialog(CombatScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        textView.setText(String.format(
                getResources().getString(R.string.combat_screen_player_victory),
                enemy.getName(),
                enemy.getGoldDrop()
                ));

        buttonPositive.setText(R.string.button_close);
        buttonPositive.setOnClickListener(view -> {
            player.setCurrentXP(player.getCurrentXP() + 2);
            player.setGold(player.getGold() + enemy.getGoldDrop());
            playerDao.insertItem(player);

            dungeon.setDungeonProgress(dungeon.getDungeonProgress() + 1);
            dungeonDao.insertItem(dungeon);

            Intent intent = new Intent(CombatScreenActivity.this, ExploreDungeonScreenActivity.class);
            intent.putExtra(PLAYER, player.getId());

            startActivity(intent);
            finish();
        });

        buttonNegative.setEnabled(false);
        buttonNegative.setVisibility(View.INVISIBLE);

        dialog.show();
    }

    private void showErrorDialogPlayerDeath(){
        final Dialog dialog = new Dialog(CombatScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        textView.setText(String.format(
                getResources().getString(R.string.combat_screen_player_defeat),
                player.getName()
                ));

        buttonPositive.setText(R.string.button_close);
        buttonPositive.setOnClickListener(view -> {
            Graveyard graveyard = new Graveyard(player.getId(), player.getName(), player.getLevel());
            GraveyardDao graveyardDao = mainDatabase.graveyardDao();
            graveyardDao.insertItem(graveyard);

            playerDao.deleteItem(player);
            dungeonDao.deleteItemFromCharacter(player.getId());
            inventoryDao.deleteItemFromCharacter(player.getId());

            Intent intent = new Intent(CombatScreenActivity.this, MainActivity.class);
            intent.putExtra(PLAYER, player.getId());

            startActivity(intent);
            finish();
        });

        buttonNegative.setEnabled(false);
        buttonNegative.setVisibility(View.INVISIBLE);

        dialog.show();
    }
}