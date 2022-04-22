package lt.vcs.baigiamasis.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import lt.vcs.baigiamasis.combat.model.Graveyard;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.Constant;
import lt.vcs.baigiamasis.dungeon.model.Dungeon;
import lt.vcs.baigiamasis.dungeon.model.Encounter;
import lt.vcs.baigiamasis.enemy.model.Enemy;
import lt.vcs.baigiamasis.inventory.model.Inventory;
import lt.vcs.baigiamasis.inventory.model.Item;

@Database(
        entities = {Player.class, Item.class, Enemy.class, Inventory.class, Encounter.class, Dungeon.class, Graveyard.class},
        version = Constant.MAIN_DATABASE_VERSION,
        exportSchema = false
)
//@TypeConverters({Converter.class})
public abstract class MainDatabase extends RoomDatabase {
    private static MainDatabase instance;

    public abstract PlayerDao playerDao();
    public abstract EnemyDao enemyDao();
    public abstract ItemDao itemDao();
    public abstract InventoryDao inventoryDao();
    public abstract EncounterDao encounterDao();
    public abstract DungeonDao dungeonDao();
    public abstract GraveyardDao graveyardDao();

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
