package lt.vcs.baigiamasis.dungeon.combat.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> combatText;

    private MaterialButton materialButtonAttack;
    private MaterialButton materialButtonSpell;
    private MaterialButton materialButtonFlee;

    private ProgressBar progressBarEnemyHP;
    private ProgressBar progressBarPlayerHP;
    private ProgressBar progressBarPlayerMP;

    private TextView textViewEnemyHP;
    private TextView textViewEnemyName;
    private TextView textViewPlayerHP;
    private TextView textViewPlayerMP;
    private TextView textViewPlayerName;

    private ImageView imageViewEnemyPortrait;
    private ImageView imageViewPlayerPortrait;

    private MainDatabase mainDatabase;
    private EnemyDao enemyDao;
    private PlayerDao playerDao;
    private DungeonDao dungeonDao;
    private EncounterDao encounterDao;
    private InventoryDao inventoryDao;

    private Enemy enemy;
    private Player player;
    private Dungeon dungeon;
    private Encounter encounter;

    private Combat combat;

    private Resources resources;

    private int enemyMaxHP;
    private int playerDamage;
    private int enemyDamage;


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

        resources = getResources();

        mainDatabase = MainDatabase.getInstance(getApplicationContext());
        enemyDao = mainDatabase.enemyDao();
        playerDao = mainDatabase.playerDao();
        dungeonDao = mainDatabase.dungeonDao();
        encounterDao = mainDatabase.encounterDao();
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
        textViewEnemyName = findViewById(R.id.textViewCombatScreenEnemyName);
        textViewEnemyName.setText(String.format(resources.getString(R.string.combat_screen_name), enemy.getName()));

        textViewEnemyHP = findViewById(R.id.textViewCombatScreenEnemyHP);
        textViewEnemyHP.setText(String.format(resources.getString(R.string.combat_screen_hp), enemy.getHealth(), enemyMaxHP));

        setUpProgressBars();
    }

    private void setUpPlayer(){
        textViewPlayerName = findViewById(R.id.textViewCombatScreenPlayerName);
        textViewPlayerName.setText(String.format(resources.getString(R.string.combat_screen_name), player.getName()));

        textViewPlayerHP = findViewById(R.id.textViewCombatScreenPlayerHP);
        textViewPlayerHP.setText(String.format(resources.getString(R.string.combat_screen_hp), player.getCurrentHealth(), player.getMaxHealth()));

        textViewPlayerMP = findViewById(R.id.textViewCombatScreenPlayerMP);
        textViewPlayerMP.setText(String.format(resources.getString(R.string.combat_screen_mp), player.getCurrentMana(), player.getMaxMana()));

        setUpProgressBars();
    }

    private void setUpCombatTextLog(){
        combatText = new ArrayList();

        listView = findViewById(R.id.listViewCombatScreenText);

        arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                combatText);

        listView.setAdapter(arrayAdapter);
    }

    private void setUpMaterialButtonAttack(){
        materialButtonAttack = findViewById(R.id.materialButtonCombatScreenAttack);
        materialButtonAttack.setOnClickListener(view -> {

            playerAttack();

            if (enemy.getHealth() > 0) enemyAttack();

        });
    }

    private void setUpMaterialButtonSpell(){
        materialButtonSpell = findViewById(R.id.materialButtonCombatScreenSpell);
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
        materialButtonFlee = findViewById(R.id.materialButtonCombatScreenFlee);
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

    private void showErrorDialogEncounter(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CombatScreenActivity.this);

        builder.setMessage(String.format(resources.getString(R.string.combat_screen_encounter), player.getName(), enemy.getName()));

        builder.setNegativeButton("Flee",
                (dialogInterface, i) -> {
                    Intent intent = new Intent(CombatScreenActivity.this, ExploreDungeonScreenActivity.class);
                    intent.putExtra(PLAYER, player.getId());

                    dungeon.setDungeonProgress(0);
                    dungeon.setDidFlee(true);

                    dungeonDao.insertItem(dungeon);
                    playerDao.insertItem(player);

                    startActivity(intent);
                    finish();
                });
        builder.setPositiveButton("Fight!", null);
        builder.show();
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
        enemyDamage = combat.calculateEnemyDamage();
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
                .make(coordinatorLayout, resources.getString(R.string.combat_screen_oom), Snackbar.LENGTH_SHORT)
                .show();
    }

    //SET-UP COMBAT LOG TEXT
    private void generateCombatLogPlayerAttack(int playerDamage){
        String log = "";

        if (playerDamage == 0) log = String.format(resources.getString(R.string.combat_screen_attack_missed), player.getName());
        if (playerDamage > 0) log = String.format(resources.getString(R.string.combat_screen_attack_hit), player.getName(), playerDamage);

        combatText.add(0, log);
        arrayAdapter.notifyDataSetChanged();
    }

    private void generateCombatLogPlayerSpell(int playerDamage){
        String log = "";

        if (playerDamage == 0) log = String.format(resources.getString(R.string.combat_screen_spell_missed), player.getName());
        if (playerDamage > 0) log = String.format(resources.getString(R.string.combat_screen_spell_hit), player.getName(), playerDamage);

        combatText.add(0, log);
        arrayAdapter.notifyDataSetChanged();
    }

    private void generateCombatLogEnemyAttack(int enemyDamage){
        String log = "";

        if (enemyDamage == 0) log = String.format(resources.getString(R.string.combat_screen_attack_missed), enemy.getName());
        if (enemyDamage > 0) log = String.format(resources.getString(R.string.combat_screen_attack_hit), enemy.getName(), enemyDamage);

        combatText.add(0, log);
        combatText.add(resources.getString(R.string.combat_screen_turn_break));
        arrayAdapter.notifyDataSetChanged();
    }

    //SET-UP VICTORY/DEFEAT
    private void showErrorDialogEnemyDeath(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CombatScreenActivity.this);

        builder.setMessage(String.format(resources.getString(R.string.combat_screen_player_victory), enemy.getName(), enemy.getGoldDrop()));

        builder.setPositiveButton("Close",
                (dialogInterface, i) -> {
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
        builder.show();
    }

    private void showErrorDialogPlayerDeath(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CombatScreenActivity.this);

        builder.setMessage(String.format(resources.getString(R.string.combat_screen_player_defeat), player.getName()));

        builder.setPositiveButton("Close",
                (dialogInterface, i) -> {
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
        builder.show();
    }
}