package lt.vcs.baigiamasis.player.ui;

import static lt.vcs.baigiamasis.Constant.PLAYER;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import lt.vcs.baigiamasis.MainGameMenuScreenActivity;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.dungeon.model.Dungeon;
import lt.vcs.baigiamasis.inventory.model.Inventory;
import lt.vcs.baigiamasis.repository.PlayerDao;
import lt.vcs.baigiamasis.repository.DungeonDao;
import lt.vcs.baigiamasis.repository.InventoryDao;
import lt.vcs.baigiamasis.repository.ItemDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class PlayerSelectScreenActivity extends AppCompatActivity {

    MaterialButton materialButton;
    EditText editText;
    ListView elementListView;
    ArrayAdapter arrayAdapter;

    List<Player> playerList;

    PlayerDao playerDao;
    ItemDao itemDao;
    InventoryDao inventoryDao;
    DungeonDao dungeonDao;

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select_screen);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        playerDao = mainDatabase.playerDao();
        itemDao = mainDatabase.itemDao();
        inventoryDao = mainDatabase.inventoryDao();
        dungeonDao = mainDatabase.dungeonDao();

        playerList = new ArrayList();
        playerList = playerDao.getAll();

        setUpListView();
        setUpCreateButton();
        setUpListViewItemClick();
        setUpListViewItemLongClick();

    }

    private void setUpCreateButton(){
        materialButton = findViewById(R.id.materialButtonCharacterSelectConfirm);
        editText = findViewById(R.id.editTextCreateCharacter);
        materialButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();

                player = new Player(0, name);
                player.setLeveledUp(true);
                player.setLevelUpPoints(10);
                playerDao.insertItem(player);

                Player createdPlayer = playerDao.getItem(playerDao.returnMaxID());

                Inventory inventory1 = new Inventory(true, createdPlayer.getId(), 1);
                inventoryDao.insertItem(inventory1);
                Inventory inventory2 = new Inventory(true, createdPlayer.getId(), 2);
                inventoryDao.insertItem(inventory2);

                Dungeon dungeon = new Dungeon(createdPlayer.getId(), 0, 2, false, false);
                dungeonDao.insertItem(dungeon);

                Intent intent = new Intent(PlayerSelectScreenActivity.this, MainGameMenuScreenActivity.class);
                intent.putExtra(PLAYER, playerDao.returnMaxID());
                startActivity(intent);
                finish();
            }
        });
    }

    private void setUpListView() {
        elementListView = findViewById(R.id.listViewMain);

        arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                playerList);

        elementListView.setAdapter(arrayAdapter);
    }

    private void setUpListViewItemClick() {
        elementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(PlayerSelectScreenActivity.this, MainGameMenuScreenActivity.class);

                Player clickedPlayer = playerList.get(position);
                intent.putExtra(PLAYER, clickedPlayer.getId());

                startActivity(intent);
//                finish(); ENABLE FOR FINAL VERSION
            }
        });
    }

    private void setUpListViewItemLongClick() {
        // Set-up clicking on specific items in the list:
        elementListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                showErrorDialog(position);
                return true;
            }
        });
    }

    public void showErrorDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayerSelectScreenActivity.this);
        builder.setMessage("Would you like to delete this character?");

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        playerDao.deleteItem(playerList.get(position));
                        inventoryDao.deleteItemFromCharacter(playerList.get(position).getId());
                        dungeonDao.deleteItemFromCharacter(playerList.get(position).getId());

                        playerList.remove(position);

                        arrayAdapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}