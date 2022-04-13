package lt.vcs.baigiamasis;

import static lt.vcs.baigiamasis.zaidimukasclasses.ItemType.ARMOR;
import static lt.vcs.baigiamasis.zaidimukasclasses.ItemType.WEAPON;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

import lt.vcs.baigiamasis.zaidimukasclasses.Item;

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
    public static Item dagger = new Item("Dagger", WEAPON, 4, 0, 0);
    public static Item clothShirt = new Item("Dagger", ARMOR, 0, 8, 0);

    MaterialButton materialButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpPlayButton();
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
}