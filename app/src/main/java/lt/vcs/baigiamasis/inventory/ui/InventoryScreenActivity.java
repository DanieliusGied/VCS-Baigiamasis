package lt.vcs.baigiamasis.inventory.ui;

import static lt.vcs.baigiamasis.Constant.CHARACTER;

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

import java.util.ArrayList;
import java.util.List;

import lt.vcs.baigiamasis.Constant;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.character.model.Character;
import lt.vcs.baigiamasis.inventory.model.Inventory;
import lt.vcs.baigiamasis.inventory.model.Item;
import lt.vcs.baigiamasis.repository.*;

public class InventoryScreenActivity extends AppCompatActivity {

    ListView elementListViewInventory;
    ListView elementListViewWeapon;
    ListView elementListViewArmor;
    ArrayAdapter arrayAdapterInventory;
    ArrayAdapter arrayAdapterWeapon;
    ArrayAdapter arrayAdapterArmor;
    Character character;
    CharacterDao characterDao;
    ItemDao itemDao;
    InventoryDao inventoryDao;

    List<Item> inventoryList;
    List<Item> weaponList;
    List<Item> armorList;


    Button buttonClose;

    private int characterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inventoryList = new ArrayList();
        weaponList = new ArrayList();
        armorList = new ArrayList();

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        characterDao = mainDatabase.characterDao();
        itemDao = mainDatabase.itemDao();
        inventoryDao = mainDatabase.inventoryDao();

        Intent intent = getIntent();
        characterID = intent.getIntExtra(CHARACTER, 0);

        character = characterDao.getItem(characterID);
        setUpItemList();

        setUpUI();
    }

    // SET UP UI:

    private void setUpItemList(){
        Item equippedWeapon = inventoryDao.getWeaponFromCharacter(character.getId());
        Item equippedArmor = inventoryDao.getArmorFromCharacter(character.getId());

        inventoryList = inventoryDao.getAllFromCharacterNotEquipped(character.getId());
        if (equippedWeapon != null) weaponList.add(equippedWeapon);
        if (equippedArmor != null) armorList.add(equippedArmor);
    }

    private void setUpUI(){
        setContentView(R.layout.activity_inventory_screen);

        setUpCloseButton();

        setUpInventoryListView();
        setUpInventoryListViewItemClick();
        setUpInventoryListViewItemLongClick();

        setUpEquippedWeaponListView();
        setUpEquippedWeaponListViewItemClick();

        setUpEquippedArmorListView();
        setUpEquippedArmorListViewItemClick();
    }

    private void setUpCloseButton(){
        buttonClose = findViewById(R.id.buttonInventoryScreenClose);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inventoryDao.deleteItemFromCharacter(character.getId());

                for (Item item : inventoryList){
                    Inventory inventory = new Inventory(false, character.getId(), item.getId());
                    inventoryDao.insertItem(inventory);
                }

                for (Item item : weaponList){
                    Inventory inventory = new Inventory(true, character.getId(), item.getId());
                    inventoryDao.insertItem(inventory);
                }

                for (Item item : armorList){
                    Inventory inventory = new Inventory(true, character.getId(), item.getId());
                    inventoryDao.insertItem(inventory);
                }

                characterDao.insertCharacter(character);
                finish();
            }
        });
    }

    // SET UP LIST VIEWS:

    private void setUpInventoryListView() {
        // Graphic element
        elementListViewInventory = findViewById(R.id.listViewInventoryScreenInventory);

        // Adapter Graphic element <-> Data
        arrayAdapterInventory = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                inventoryList);

        // Graphic element connected with adapter
        elementListViewInventory.setAdapter(arrayAdapterInventory);
    }

    private void setUpEquippedWeaponListView() {
        // Graphic element
        elementListViewWeapon = findViewById(R.id.listViewInventoryScreenEquippedWeapon);

        // Adapter Graphic element <-> Data
        arrayAdapterWeapon = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                weaponList);

        // Graphic element connected with adapter
        elementListViewWeapon.setAdapter(arrayAdapterWeapon);
    }

    private void setUpEquippedArmorListView() {
        // Graphic element
        elementListViewArmor = findViewById(R.id.listViewInventoryScreenEquippedArmor);

        // Adapter Graphic element <-> Data
        arrayAdapterArmor = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                armorList);

        // Graphic element connected with adapter
        elementListViewArmor.setAdapter(arrayAdapterArmor);
    }

    // SET UP LIST VIEW ITEM CLICK:

    private void setUpInventoryListViewItemClick() {
        // Set-up clicking on specific items in the list:
        elementListViewInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Item clickedItem = inventoryList.get(position);

                showErrorDialogEquipItem(clickedItem, position);
            }
        });
    }

    private void setUpInventoryListViewItemLongClick() {
        // Set-up clicking on specific items in the list:
        elementListViewInventory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Log.i("test_tag", "Just long-clicked the item " + position + " from the list :)");
//                Log.i("test_tag", noteListText.get(position).toString());
                showErrorDialogDiscardItem(position);
                return true;
            }
        });
    }

    private void setUpEquippedWeaponListViewItemClick(){
        elementListViewWeapon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Item clickedItem = weaponList.get(position);

                showErrorDialogUnequipItem(clickedItem, position);
            }
        });
    }

    private void setUpEquippedArmorListViewItemClick(){
        elementListViewArmor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Item clickedItem = armorList.get(position);

                showErrorDialogUnequipItem(clickedItem, position);
            }
        });
    }

    // SET UP ERROR DIALOG BOXES:

    public void showErrorDialogDiscardItem(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(InventoryScreenActivity.this);

        Item clickedItem = inventoryList.get(position);

        builder.setMessage("Would you like to DISCARD this item? The item will be destroyed.");

        builder.setPositiveButton("DISCARD",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inventoryList.remove(clickedItem);
                        arrayAdapterInventory.notifyDataSetChanged();
                        characterDao.insertCharacter(character);
                    }
                });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void showErrorDialogEquipItem(Item item, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(InventoryScreenActivity.this);

        builder.setMessage(item.toString());

        builder.setPositiveButton("Equip",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        equipItem(item, position);
                        arrayAdapterInventory.notifyDataSetChanged();
                        arrayAdapterArmor.notifyDataSetChanged();
                        arrayAdapterWeapon.notifyDataSetChanged();
                        characterDao.insertCharacter(character);
                    }
                });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void showErrorDialogUnequipItem(Item item, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(InventoryScreenActivity.this);
        builder.setMessage(item.toString());

        builder.setPositiveButton("Un-equip",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        unequipItem(item, position);
                        arrayAdapterInventory.notifyDataSetChanged();
                        arrayAdapterArmor.notifyDataSetChanged();
                        arrayAdapterWeapon.notifyDataSetChanged();
                        characterDao.insertCharacter(character);
                    }
                });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // SET UP EQUIP/UNEQUIP ITEM FUNCTIONALITY:

    private void unequipItem(Item item, int position){

        Inventory inventory1;
        Inventory inventory2;

        switch (item.getItemType()){
            case Constant.WEAPON:
                Item equippedWeapon = weaponList.get(position);
                inventoryList.add(equippedWeapon);
                weaponList.remove(position);

                inventory1 = new Inventory(false, character.getId(), equippedWeapon.getId());
                inventory2 = new Inventory(true, character.getId(), item.getId());
                inventoryDao.insertItem(inventory1);
                inventoryDao.insertItem(inventory2);
                inventoryDao.deleteItem(inventoryDao.returnWeaponId(character.getId()));
                break;
            case Constant.ARMOR:
                Item equippedArmor = armorList.get(position);
                inventoryList.add(equippedArmor);
                armorList.remove(position);

                inventory1 = new Inventory(false, character.getId(), equippedArmor.getId());
                inventory2 = new Inventory(true, character.getId(), item.getId());
                inventoryDao.insertItem(inventory1);
                inventoryDao.insertItem(inventory2);
                inventoryDao.deleteItem(inventoryDao.returnArmorId(character.getId()));
                break;
            default:
                break;
        }
    }

    private void equipItem(Item itemBeingEquipped, int position){
        Item itemBeingUnequipped;
        Inventory inventory;

        switch (itemBeingEquipped.getItemType()){
            case Constant.WEAPON:

                if (weaponList.isEmpty() == false) {
                    itemBeingUnequipped = weaponList.get(0);
                    weaponList.remove(0);
                    inventoryList.add(itemBeingUnequipped);
                }

                weaponList.add(itemBeingEquipped);

                inventoryDao.deleteItem(inventoryDao.returnWeaponId(character.getId()));
                break;
            case Constant.ARMOR:
                if (armorList.isEmpty() == false) {
                    itemBeingUnequipped = armorList.get(0);
                    armorList.remove(0);
                    inventoryList.add(itemBeingUnequipped);
                }

                armorList.add(itemBeingEquipped);

                inventoryDao.deleteItem(inventoryDao.returnArmorId(character.getId()));
                break;
            default:
                break;

        }

        inventoryList.remove(position);

        inventory = new Inventory(true, character.getId(), itemBeingEquipped.getId());
        inventoryDao.insertItem(inventory);
    }
}