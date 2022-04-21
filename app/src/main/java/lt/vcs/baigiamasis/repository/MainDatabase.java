package lt.vcs.baigiamasis.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import lt.vcs.baigiamasis.character.model.Character;
import lt.vcs.baigiamasis.Constant;
import lt.vcs.baigiamasis.dungeon.model.Encounter;
import lt.vcs.baigiamasis.enemy.model.Enemy;
import lt.vcs.baigiamasis.inventory.model.Inventory;
import lt.vcs.baigiamasis.inventory.model.Item;

@Database(
        entities = {Character.class, Item.class, Enemy.class, Inventory.class, Encounter.class},
        version = Constant.MAIN_DATABASE_VERSION,
        exportSchema = false
)
//@TypeConverters({Converter.class})
public abstract class MainDatabase extends RoomDatabase {
    private static MainDatabase instance;

    public abstract CharacterDao characterDao();
    public abstract EnemyDao enemyDao();
    public abstract ItemDao itemDao();
    public abstract InventoryDao inventoryDao();
    public abstract EncounterDao encounterDao();

    public static synchronized MainDatabase getInstance(Context context){
        instance = Room.databaseBuilder(
                context,
                MainDatabase.class,
                "main.db"
        ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        return instance;
    }
}
