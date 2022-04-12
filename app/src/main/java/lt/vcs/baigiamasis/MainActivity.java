package lt.vcs.baigiamasis;

import static lt.vcs.baigiamasis.zaidimukasclasses.ItemType.ARMOR;
import static lt.vcs.baigiamasis.zaidimukasclasses.ItemType.WEAPON;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lt.vcs.baigiamasis.repository.CharacterDao;
import lt.vcs.baigiamasis.repository.MainDatabase;
import lt.vcs.baigiamasis.zaidimukasclasses.Character;
import lt.vcs.baigiamasis.zaidimukasclasses.Constant;
import lt.vcs.baigiamasis.zaidimukasclasses.Item;

public class MainActivity extends AppCompatActivity {

//    public static Blacksmith blacksmith = new Blacksmith();
//    public static Dungeon dungeon = new Dungeon();
//    public static Room room = new Room();
//    public static Room roomPuzzle = new RoomPuzzle();
//    public static Room roomCombat = new RoomCombat();
//    public static Room roomTreasure = new RoomTreasure();
//    public static Room roomBoss = new RoomBoss();
    public static Random random = new Random();
//    public static Enemy enemy = new Enemy();
//    public static Enemy goblin = new Goblin();
//    public static Combat combat = new Combat();
    public static Item dagger = new Item("Dagger", WEAPON, 4, 0, 0);
//    public static Item sword = new Item("Sword", WEAPON, 6, 10);
//    public static Item longSword = new Item("Longsword", WEAPON, 8, 25);
//    public static Item greatSword = new Item("Greatsword", WEAPON, 10, 50);
    public static Item clothShirt = new Item("Dagger", ARMOR, 0, 8, 0);
//    public static Item leatherTunic = new Item("Dagger", 10, ARMOR, 10);
//    public static Item chainVest = new Item("Dagger", 12, ARMOR, 25);
//    public static Item plateMail = new Item("Dagger", 15, ARMOR, 50);
    MaterialButton materialButton;
    EditText editText;
    List<Character> characterList;
    ListView elementListView;
    ArrayAdapter arrayAdapter;
    CharacterDao characterDao;
    public static Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }


    private void setUpCreateButton(){
        materialButton = findViewById(R.id.materialButtonMainConfirm);
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
                Constant.CHARACTER_ID = characterDao.returnMaxID();

                Intent intent = new Intent(MainActivity.this, SecondScreenActivity.class);
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
}