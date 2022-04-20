package lt.vcs.baigiamasis;

import static lt.vcs.baigiamasis.inventory.model.ItemType.ARMOR;
import static lt.vcs.baigiamasis.inventory.model.ItemType.WEAPON;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

import lt.vcs.baigiamasis.character.ui.CharacterSelectScreenActivity;
import lt.vcs.baigiamasis.inventory.model.Inventory;
import lt.vcs.baigiamasis.inventory.model.Item;
import lt.vcs.baigiamasis.repository.InventoryDao;
import lt.vcs.baigiamasis.repository.ItemDao;
import lt.vcs.baigiamasis.repository.MainDatabase;

public class MainActivity extends AppCompatActivity {

//    public static Blacksmith blacksmith = new Blacksmith();
//    public static Dungeon dungeon = new Dungeon();
//    public static Room room = new Room();
//    public static Room roomPuzzle = new RoomPuzzle();
//    public static Room roomCombat = new RoomCombat();
//    public static Room roomTreasure = new RoomTreasure();
//    public static Room roomBoss = new RoomBoss();
//    public static Item leatherTunic = new Item("Dagger", 10, ARMOR, 10);
//    public static Item chainVest = new Item("Dagger", 12, ARMOR, 25);
//    public static Item plateMail = new Item("Dagger", 15, ARMOR, 50);
//    public static Enemy enemy = new Enemy();
//    public static Enemy goblin = new Goblin();
//    public static Combat combat = new Combat();
//    public static Item sword = new Item("Sword", WEAPON, 6, 10);
//    public static Item longSword = new Item("Longsword", WEAPON, 8, 25);
//    public static Item greatSword = new Item("Greatsword", WEAPON, 10, 50);

    public static Random random = new Random();
    public static int characterID;

    MaterialButton materialButton;
    MainDatabase mainDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainDatabase = MainDatabase.getInstance(getApplicationContext());

        setUpPlayButton();
        setUpItems();
    }


    private void setUpPlayButton(){
        materialButton = findViewById(R.id.materialButtonMainPlay);
        materialButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CharacterSelectScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setUpItems(){
        Item dagger = new Item(1, "Dagger", Constant.WEAPON, 4, 0, 0);
        Item clothShirt = new Item(2, "Cloth armor", Constant.ARMOR, 0, 8, 0);

        ItemDao itemDao = mainDatabase.itemDao();

        itemDao.insertItem(dagger);
        itemDao.insertItem(clothShirt);
    }

    private void setUpInventory(){
        InventoryDao inventoryDao = mainDatabase.inventoryDao();

        Inventory inventory1 = new Inventory(true, 1, 1);
        Inventory inventory2 = new Inventory(true, 1, 2);
    }
}