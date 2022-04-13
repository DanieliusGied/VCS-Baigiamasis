package lt.vcs.baigiamasis;

import static lt.vcs.baigiamasis.zaidimukasclasses.Constant.CHARACTER_ID;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.zaidimukasclasses.Character;
import lt.vcs.baigiamasis.zaidimukasclasses.Constant;

public class CharacterSelectScreenActivity extends AppCompatActivity {

    MaterialButton materialButton;
    EditText editText;
    List<Character> characterList;
    ListView elementListView;
    ArrayAdapter arrayAdapter;
    CharacterDao characterDao;
    Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select_screen);

        MainDatabase database = Room.databaseBuilder(
                getApplicationContext(),
                MainDatabase.class,
                "main.db"
        ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        characterDao = database.characterDao();
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
                Log.i("TEST_TAG", "Button clicked");
                String name = editText.getText().toString();
                Log.i("TEST_TAG", "Text read: " + name);
                character = new Character(0, name);
                Log.i("TEST_TAG", "character generated: " + character);
                characterDao.insertCharacter(character);
                Log.i("TEST_TAG", "character inserted into db");
                CHARACTER_ID = characterDao.returnMaxID();
                Log.i("TEST_TAG", "now the ID we're working with is: " + CHARACTER_ID + " and the variable id is: " + character.id);

                Intent intent = new Intent(CharacterSelectScreenActivity.this, MainGameMenuScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setUpListView() {
        // Graphic element
        elementListView = findViewById(R.id.listViewMain);

        // Adapter Graphic element <-> Data
        arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                characterList);

        // Graphic element connected with adapter
        elementListView.setAdapter(arrayAdapter);
    }

    private void setUpListViewItemClick() {
        elementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CharacterSelectScreenActivity.this, MainGameMenuScreenActivity.class);

                Character clickedCharacter = characterList.get(position);
                CHARACTER_ID = clickedCharacter.id;

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
                        characterList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}