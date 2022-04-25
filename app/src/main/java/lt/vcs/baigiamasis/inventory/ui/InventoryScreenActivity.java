package lt.vcs.baigiamasis.inventory.ui;

import static lt.vcs.baigiamasis.common.Constant.PLAYER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.R;
import lt.vcs.baigiamasis.player.model.Player;
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
    Player player;
    PlayerDao playerDao;
    ItemDao itemDao;
    InventoryDao inventoryDao;

    List<Item> inventoryList;
    List<Item> weaponList;
    List<Item> armorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inventoryList = new ArrayList();
        weaponList = new ArrayList();
        armorList = new ArrayList();

        setUpDatabase();
        setUpItemList();
        setUpUI();
    }

    //SET-UP DATABASE
    private void setUpDatabase() {
        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        playerDao = mainDatabase.playerDao();
        itemDao = mainDatabase.itemDao();
        inventoryDao = mainDatabase.inventoryDao();

        Intent intent = getIntent();
        int characterID = intent.getIntExtra(PLAYER, 0);

        player = playerDao.getItem(characterID);
    }

    //SET-UP UI:
    private void setUpItemList(){
        Item equippedWeapon = inventoryDao.getWeaponFromCharacter(player.getId());
        Item equippedArmor = inventoryDao.getArmorFromCharacter(player.getId());

        inventoryList = inventoryDao.getAllFromCharacterNotEquipped(player.getId());
        if (equippedWeapon != null) weaponList.add(equippedWeapon);
        if (equippedArmor != null) armorList.add(equippedArmor);
    }

    private void setUpUI(){
        setContentView(R.layout.activity_inventory_screen);

        setUpCloseButton();

        setUpGoldText();

        setUpInventoryListView();
        setUpInventoryListViewItemClick();
        setUpInventoryListViewItemLongClick();

        setUpEquippedWeaponListView();
        setUpEquippedWeaponListViewItemClick();

        setUpEquippedArmorListView();
        setUpEquippedArmorListViewItemClick();
    }

    private void setUpCloseButton(){
        Button buttonClose = findViewById(R.id.buttonInventoryScreenClose);

        buttonClose.setOnClickListener(view -> {

            inventoryDao.deleteItemFromCharacter(player.getId());

            inventoryList.stream()
                    .map(item -> new Inventory(false, player.getId(), item.getId()))
                    .forEach(inventory -> inventoryDao.insertItem(inventory));

            weaponList.stream()
                    .map(item -> new Inventory(true, player.getId(), item.getId()))
                    .forEach(inventory -> inventoryDao.insertItem(inventory));

            armorList.stream()
                    .map(item -> new Inventory(true, player.getId(), item.getId()))
                    .forEach(inventory -> inventoryDao.insertItem(inventory));

            playerDao.insertItem(player);
            finish();
        });
    }

    private void setUpGoldText(){
        TextView textView = findViewById(R.id.textViewInventoryScreenGold);
        textView.setText(String.format(
                getResources().getString(R.string.inventory_gold),
                player.getGold()
        ));
    }

    //SET-UP LIST VIEWS:
    private void setUpInventoryListView() {
        elementListViewInventory = findViewById(R.id.listViewInventoryScreenInventory);

        arrayAdapterInventory = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                inventoryList){
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

        elementListViewInventory.setAdapter(arrayAdapterInventory);
    }

    private void setUpEquippedWeaponListView() {
        elementListViewWeapon = findViewById(R.id.listViewInventoryScreenEquippedWeapon);

        arrayAdapterWeapon = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                weaponList){
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

        elementListViewWeapon.setAdapter(arrayAdapterWeapon);
    }

    private void setUpEquippedArmorListView() {
        elementListViewArmor = findViewById(R.id.listViewInventoryScreenEquippedArmor);

        arrayAdapterArmor = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                armorList){
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

        elementListViewArmor.setAdapter(arrayAdapterArmor);
    }

    //SET-UP LIST VIEW ITEM CLICK:
    private void setUpInventoryListViewItemClick() {
        // Set-up clicking on specific items in the list:
        elementListViewInventory.setOnItemClickListener((adapterView, view, position, id) -> {
            Item clickedItem = inventoryList.get(position);

            showErrorDialogEquipItem(clickedItem, position);
        });
    }

    private void setUpInventoryListViewItemLongClick() {
        elementListViewInventory.setOnItemLongClickListener((adapterView, view, position, id) -> {
            showErrorDialogDiscardItem(position);
            return true;
        });
    }

    private void setUpEquippedWeaponListViewItemClick(){
        elementListViewWeapon.setOnItemClickListener((adapterView, view, position, id) -> {
            Item clickedItem = weaponList.get(position);

            showErrorDialogUnequipItem(clickedItem, position);
        });
    }

    private void setUpEquippedArmorListViewItemClick(){
        elementListViewArmor.setOnItemClickListener((adapterView, view, position, id) -> {
            Item clickedItem = armorList.get(position);

            showErrorDialogUnequipItem(clickedItem, position);
        });
    }

    //SET-UP ERROR DIALOG BOXES:
    public void showErrorDialogDiscardItem(int position){
        final Dialog dialog = new Dialog(InventoryScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        Item clickedItem = inventoryList.get(position);

        textView.setText(R.string.inventory_screen_discard_item);
        buttonPositive.setText(R.string.inventory_screen_discard_confirm);

        buttonPositive.setOnClickListener(view -> {
            inventoryList.remove(clickedItem);
            arrayAdapterInventory.notifyDataSetChanged();
            playerDao.insertItem(player);
            dialog.dismiss();
        });

        buttonNegative.setEnabled(false);
        buttonNegative.setVisibility(View.INVISIBLE);

        dialog.show();
    }

    public void showErrorDialogEquipItem(Item item, int position){
        final Dialog dialog = new Dialog(InventoryScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        if (item.getItemType().equals(Constant.WEAPON)) {
            textView.setText(String.format(
                    getResources().getString(R.string.inventory_screen_item_text_weapon),
                    item.getName(),
                    item.getMaxDamage()));
        }
        if (item.getItemType().equals(Constant.ARMOR)) {
            textView.setText(String.format(
                    getResources().getString(R.string.inventory_screen_item_text_armor),
                    item.getName(),
                    item.getArmor()));
        }
        buttonPositive.setText(R.string.inventory_screen_equip);

        buttonPositive.setOnClickListener(view -> {
            equipItem(item, position);
            arrayAdapterInventory.notifyDataSetChanged();
            arrayAdapterArmor.notifyDataSetChanged();
            arrayAdapterWeapon.notifyDataSetChanged();
            playerDao.insertItem(player);
            dialog.dismiss();
        });

        buttonNegative.setEnabled(false);
        buttonNegative.setVisibility(View.INVISIBLE);

        dialog.show();
    }

    public void showErrorDialogUnequipItem(Item item, int position){
        final Dialog dialog = new Dialog(InventoryScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        TextView textView = dialog.findViewById(R.id.dialogTextView);
        MaterialButton buttonPositive = dialog.findViewById(R.id.dialogPositiveButton);
        MaterialButton buttonNegative = dialog.findViewById(R.id.dialogNegativeButton);

        if (item.getItemType().equals(Constant.WEAPON)) {
            textView.setText(String.format(
                    getResources().getString(R.string.inventory_screen_item_text_weapon),
                    item.getName(),
                    item.getMaxDamage()));
        }
        if (item.getItemType().equals(Constant.ARMOR)) {
            textView.setText(String.format(
                    getResources().getString(R.string.inventory_screen_item_text_armor),
                    item.getName(),
                    item.getArmor()));
        }
        buttonPositive.setText(R.string.inventory_screen_unequip);

        buttonPositive.setOnClickListener(view -> {
            unequipItem(item, position);
            arrayAdapterInventory.notifyDataSetChanged();
            arrayAdapterArmor.notifyDataSetChanged();
            arrayAdapterWeapon.notifyDataSetChanged();
            playerDao.insertItem(player);
            dialog.dismiss();
        });

        buttonNegative.setEnabled(false);
        buttonNegative.setVisibility(View.INVISIBLE);

        dialog.show();
    }

    //SET-UP EQUIP/UNEQUIP ITEM FUNCTIONALITY:
    private void unequipItem(Item item, int position){

        Inventory inventory1;
        Inventory inventory2;

        switch (item.getItemType()){
            case Constant.WEAPON:
                Item equippedWeapon = weaponList.get(position);
                inventoryList.add(equippedWeapon);
                weaponList.remove(position);

                inventory1 = new Inventory(false, player.getId(), equippedWeapon.getId());
                inventory2 = new Inventory(true, player.getId(), item.getId());
                inventoryDao.insertItem(inventory1);
                inventoryDao.insertItem(inventory2);
                inventoryDao.deleteItem(inventoryDao.returnWeaponId(player.getId()));
                break;
            case Constant.ARMOR:
                Item equippedArmor = armorList.get(position);
                inventoryList.add(equippedArmor);
                armorList.remove(position);

                inventory1 = new Inventory(false, player.getId(), equippedArmor.getId());
                inventory2 = new Inventory(true, player.getId(), item.getId());
                inventoryDao.insertItem(inventory1);
                inventoryDao.insertItem(inventory2);
                inventoryDao.deleteItem(inventoryDao.returnArmorId(player.getId()));
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

                if (!weaponList.isEmpty()) {
                    itemBeingUnequipped = weaponList.get(0);
                    weaponList.remove(0);
                    inventoryList.add(itemBeingUnequipped);
                }

                weaponList.add(itemBeingEquipped);

                inventoryDao.deleteItem(inventoryDao.returnWeaponId(player.getId()));
                break;
            case Constant.ARMOR:
                if (!armorList.isEmpty()) {
                    itemBeingUnequipped = armorList.get(0);
                    armorList.remove(0);
                    inventoryList.add(itemBeingUnequipped);
                }

                armorList.add(itemBeingEquipped);

                inventoryDao.deleteItem(inventoryDao.returnArmorId(player.getId()));
                break;
            default:
                break;

        }

        inventoryList.remove(position);

        inventory = new Inventory(true, player.getId(), itemBeingEquipped.getId());
        inventoryDao.insertItem(inventory);
    }
}