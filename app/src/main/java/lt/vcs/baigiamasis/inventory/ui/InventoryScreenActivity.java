package lt.vcs.baigiamasis.inventory.ui;

import static lt.vcs.baigiamasis.Constant.CHARACTER;
import static lt.vcs.baigiamasis.inventory.model.ItemType.ARMOR;
import static lt.vcs.baigiamasis.inventory.model.ItemType.WEAPON;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.character.model.Character;
import lt.vcs.baigiamasis.inventory.model.Item;
import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.ItemDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class InventoryScreenActivity extends AppCompatActivity {

    ListView elementListView;
    ArrayAdapter arrayAdapter;
    Character character;
    CharacterDao characterDao;
    ItemDao itemDao;

    Button buttonWeapon;
    Button buttonArmor;
    Button buttonClose;

    private int characterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUI();

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        characterDao = mainDatabase.characterDao();
        itemDao = mainDatabase.itemDao();

        Intent intent = getIntent();
        characterID = intent.getIntExtra(CHARACTER, 0);

        character = characterDao.getItem(characterID);

    }

    private void setUpUI(){
        setContentView(R.layout.activity_inventory_screen);
        buttonWeapon = findViewById(R.id.buttonInventoryScreenEquippedWeapon);
        buttonArmor = findViewById(R.id.buttonInventoryScreenEquippedArmor);
        setUpCloseButton();
        setUpEquippedItemButtonText();
        setUpWeaponButtonClick();
        setUpArmorButtonClick();

        setUpInventoryListView();
        setUpListViewItemClick();
        setUpListViewItemLongClick();
    }

    private void setUpCloseButton(){
        buttonClose = findViewById(R.id.buttonInventoryScreenClose);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                characterDao.insertCharacter(character);
                finish();
            }
        });
    }

    private void setUpEquippedItemButtonText(){
        buttonWeapon.setText(character.getEquippedItems().get(WEAPON).getName());
        buttonArmor.setText(character.getEquippedItems().get(ARMOR).getName());
    }

    private void setUpWeaponButtonClick(){
        buttonWeapon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showErrorDialogUnequipItem(character.getEquippedItems().get(WEAPON));
            }
        });
    }

    private void setUpArmorButtonClick(){
        buttonArmor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showErrorDialogUnequipItem(character.getEquippedItems().get(ARMOR));
            }
        });
    }

    private void setUpInventoryListView() {
        // Graphic element
        elementListView = findViewById(R.id.listViewInventoryScreenInventory);

        // Adapter Graphic element <-> Data
        arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                character.getInventoryList());

        // Graphic element connected with adapter
        elementListView.setAdapter(arrayAdapter);
    }

    private void setUpListViewItemClick() {
        // Set-up clicking on specific items in the list:
        elementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showErrorDialogEquipItem(position);
            }
        });
    }

    private void setUpListViewItemLongClick() {
        // Set-up clicking on specific items in the list:
        elementListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Log.i("test_tag", "Just long-clicked the item " + position + " from the list :)");
//                Log.i("test_tag", noteListText.get(position).toString());
                showErrorDialogDiscardItem(position);
                return true;
            }
        });
    }

    public void showErrorDialogEquipItem(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(InventoryScreenActivity.this);

        Item clickedItem = character.getInventoryList().get(position);

        builder.setMessage(clickedItem.toString());

        builder.setPositiveButton("Equip",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        character.equipItem(clickedItem);
                        arrayAdapter.notifyDataSetChanged();
                        setUpEquippedItemButtonText();
                        characterDao.insertCharacter(character);
                    }
                });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void showErrorDialogDiscardItem(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(InventoryScreenActivity.this);

        Item clickedItem = character.getInventoryList().get(position);

        builder.setMessage("Would you like to DISCARD this item? The item will be destroyed.");

        builder.setPositiveButton("DISCARD",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        character.getInventoryList().remove(clickedItem);
                        arrayAdapter.notifyDataSetChanged();
                        characterDao.insertCharacter(character);
                    }
                });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void showErrorDialogUnequipItem(Item item){
        AlertDialog.Builder builder = new AlertDialog.Builder(InventoryScreenActivity.this);
        builder.setMessage(item.toString());

        builder.setPositiveButton("Un-equip",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        character.unequipItem(item);
                        characterDao.insertCharacter(character);
                    }
                });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


}