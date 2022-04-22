package lt.vcs.baigiamasis.combat.ui;

import static lt.vcs.baigiamasis.Constant.PLAYER;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import lt.vcs.baigiamasis.Constant;
import lt.vcs.baigiamasis.MainActivity;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.combat.model.Graveyard;
import lt.vcs.baigiamasis.dungeon.ui.ExploreDungeonScreenActivity;
import lt.vcs.baigiamasis.inventory.ui.InventoryScreenActivity;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.combat.model.Combat;
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

        enemyMaxHP = enemy.getHealth();

        int playerWeaponDamage = inventoryDao.getWeaponFromCharacter(player.getId()).getMaxDamage();
        int playerArmor = inventoryDao.getArmorFromCharacter(player.getId()).getArmor() + player.getArmor();

        combat = new Combat(player, enemy, playerWeaponDamage, playerArmor);

        setUpUI();

        // TODO: 4/22/2022 FIX BUG WHEN NO WEAPON IS EQUIPPED
    }

    //SET-UP UI
    private void setUpUI(){
        setContentView(R.layout.activity_combat_screen);
        setUpCombatTextLog();

        setUpMaterialButtonAttack();
        setUpMaterialButtonSpell();
        setUpMaterialButtonFlee();

        setUpEnemy();
        setUpPlayer();
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

    private void generateCombatLogPlayerAttack(int playerDamage){
        String log = "";

        if (playerDamage == 0) log = String.format(resources.getString(R.string.combat_screen_attack_missed), player.getName());
        if (playerDamage > 0) log = String.format(resources.getString(R.string.combat_screen_attack_hit), player.getName(), playerDamage);

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

    private void setUpMaterialButtonSpell(){
        materialButtonSpell = findViewById(R.id.materialButtonCombatScreenSpell);
        materialButtonSpell.setOnClickListener(view -> {

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

    private void setUpEnemy(){
        textViewEnemyName = findViewById(R.id.textViewCombatScreenEnemyName);
        textViewEnemyName.setText(String.format(resources.getString(R.string.combat_screen_name), enemy.getName()));

        textViewEnemyHP = findViewById(R.id.textViewCombatScreenEnemyHP);
        textViewEnemyHP.setText(String.format(resources.getString(R.string.combat_screen_hp_mp), enemy.getHealth(), enemyMaxHP));
    }

    private void setUpPlayer(){
        textViewPlayerName = findViewById(R.id.textViewCombatScreenPlayerName);
        textViewPlayerName.setText(String.format(resources.getString(R.string.combat_screen_name), player.getName()));

        textViewPlayerHP = findViewById(R.id.textViewCombatScreenPlayerHP);
        textViewPlayerHP.setText(String.format(resources.getString(R.string.combat_screen_hp_mp), player.getCurrentHealth(), player.getMaxHealth()));

        textViewPlayerMP = findViewById(R.id.textViewCombatScreenPlayerMP);
        textViewPlayerMP.setText(String.format(resources.getString(R.string.combat_screen_hp_mp), player.getCurrentMana(), player.getMaxMana()));
    }
}