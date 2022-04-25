package lt.vcs.baigiamasis.mainmenu.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.inventory.ui.InventoryScreenActivity;
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
        setUpDatabase();
        setUpUI();
    }

    //SET-UP DATABASE
    private void setUpDatabase() {
        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        playerDao = mainDatabase.playerDao();
        itemDao = mainDatabase.itemDao();
        inventoryDao = mainDatabase.inventoryDao();
        dungeonDao = mainDatabase.dungeonDao();

        playerList = new ArrayList();
        playerList = playerDao.getAll();
    }

    //SET-UP UI
    private void setUpUI(){
        setContentView(R.layout.activity_player_select_screen);

        setUpListView();
        setUpCreateButton();
        setUpListViewItemClick();
        setUpListViewItemLongClick();
    }

    //SET-UP CREATE CHARACTER FUNCTIONALITY
    private void setUpCreateButton(){
        materialButton = findViewById(R.id.materialButtonCharacterSelectConfirm);
        editText = findViewById(R.id.editTextCreateCharacter);
        materialButton.setOnClickListener(view -> {
            createCharacter();
        });
    }

    private void createCharacter(){
        String name = editText.getText().toString();

        player = new Player(0, name);
        playerDao.insertItem(player);

        Player createdPlayer = playerDao.getItem(playerDao.returnMaxID());

        Inventory inventory1 = new Inventory(true, createdPlayer.getId(), 1);
        Inventory inventory2 = new Inventory(true, createdPlayer.getId(), 2);
        Dungeon dungeon = new Dungeon(createdPlayer.getId(), 0, 2, false, false);
        inventoryDao.insertItem(inventory1);
        inventoryDao.insertItem(inventory2);
        dungeonDao.insertItem(dungeon);

        Intent intent = new Intent(PlayerSelectScreenActivity.this, MainGameMenuScreenActivity.class);
        intent.putExtra(PLAYER, playerDao.returnMaxID());
        startActivity(intent);
        finish();
    }

    //SET-UP LOAD CHARACTER FUNCTIONALITY
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
        final Dialog dialog = new Dialog(PlayerSelectScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        textView.setText(R.string.player_select_delete_confirmation);
        buttonPositive.setText(R.string.button_yes);
        buttonNegative.setText(R.string.button_no);

        buttonPositive.setOnClickListener(view -> {
            playerDao.deleteItem(playerList.get(position));
            inventoryDao.deleteItemFromCharacter(playerList.get(position).getId());
            dungeonDao.deleteItemFromCharacter(playerList.get(position).getId());

            playerList.remove(position);

            arrayAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        buttonNegative.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
}