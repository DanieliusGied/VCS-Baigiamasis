package lt.vcs.baigiamasis.mainmenu.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

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
        materialButton.setOnClickListener(view -> {
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
        });
    }

    private void setUpListView() {
        elementListView = findViewById(R.id.listViewMain);

        arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                playerList){
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

        elementListView.setAdapter(arrayAdapter);
    }

    private void setUpListViewItemClick() {
        elementListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(PlayerSelectScreenActivity.this, MainGameMenuScreenActivity.class);

            Player clickedPlayer = playerList.get(position);
            intent.putExtra(PLAYER, clickedPlayer.getId());

            startActivity(intent);
            finish();
        });
    }

    private void setUpListViewItemLongClick() {
        elementListView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            showErrorDialog(position);
            return true;
        });
    }

    public void showErrorDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayerSelectScreenActivity.this);
        builder.setMessage("Would you like to delete this character?");

        builder.setPositiveButton("Yes",
                (dialogInterface, i) -> {
                    playerDao.deleteItem(playerList.get(position));
                    inventoryDao.deleteItemFromCharacter(playerList.get(position).getId());
                    dungeonDao.deleteItemFromCharacter(playerList.get(position).getId());

                    playerList.remove(position);

                    arrayAdapter.notifyDataSetChanged();
                });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}