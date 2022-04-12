package lt.vcs.baigiamasis.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import lt.vcs.baigiamasis.zaidimukasclasses.Character;
import lt.vcs.baigiamasis.zaidimukasclasses.Constant;
import lt.vcs.baigiamasis.zaidimukasclasses.Enemy;
import lt.vcs.baigiamasis.zaidimukasclasses.Item;

@Database(
        entities = {Character.class, Item.class, Enemy.class},
        version = Constant.MAIN_DATABASE_VERSION,
        exportSchema = false
)
@TypeConverters({Converter.class})
public abstract class MainDatabase extends RoomDatabase {
    public abstract CharacterDao characterDao();
    public abstract EnemyDao enemyDao();
    public abstract ItemDao itemDao();
}
