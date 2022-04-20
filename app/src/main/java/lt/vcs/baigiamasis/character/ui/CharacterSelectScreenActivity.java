package lt.vcs.baigiamasis.character.ui;

import static lt.vcs.baigiamasis.Constant.CHARACTER;

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
import lt.vcs.baigiamasis.inventory.model.Inventory;
import lt.vcs.baigiamasis.inventory.model.Item;
import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.InventoryDao;
import lt.vcs.baigiamasis.repository.ItemDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.character.model.Character;

public class CharacterSelectScreenActivity extends AppCompatActivity {

    MaterialButton materialButton;
    EditText editText;
    List<Character> characterList;
    ListView elementListView;
    ArrayAdapter arrayAdapter;
    CharacterDao characterDao;
    ItemDao itemDao;
    InventoryDao inventoryDao;
    Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select_screen);

        MainDatabase mainDatabase = MainDatabase.getInstance(getApplicationContext());
        characterDao = mainDatabase.characterDao();
        itemDao = mainDatabase.itemDao();
        inventoryDao = mainDatabase.inventoryDao();

        characterList = new ArrayList();
        characterList = characterDao.getAll();

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
                Item weaponItem = itemDao.getItem(1);
                Item armorItem = itemDao.getItem(2);

                character = new Character(0, name);
                characterDao.insertCharacter(character);

                Character createdCharacter = characterDao.getItem(characterDao.returnMaxID());

                Inventory inventory1 = new Inventory(true, createdCharacter.getId(), 1);
                inventoryDao.insertItem(inventory1);
                Inventory inventory2 = new Inventory(true, createdCharacter.getId(), 2);
                inventoryDao.insertItem(inventory2);

                Intent intent = new Intent(CharacterSelectScreenActivity.this, MainGameMenuScreenActivity.class);
                intent.putExtra(CHARACTER, characterDao.returnMaxID());
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
                characterList);

        elementListView.setAdapter(arrayAdapter);
    }

    private void setUpListViewItemClick() {
        elementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CharacterSelectScreenActivity.this, MainGameMenuScreenActivity.class);

                Character clickedCharacter = characterList.get(position);
                intent.putExtra(CHARACTER, clickedCharacter.getId());

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
        AlertDialog.Builder builder = new AlertDialog.Builder(CharacterSelectScreenActivity.this);
        builder.setMessage("Would you like to delete this character?");

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        characterDao.deleteCharacter(characterList.get(position));
                        inventoryDao.deleteItemFromCharacter(characterList.get(position).getId());
                        characterList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}