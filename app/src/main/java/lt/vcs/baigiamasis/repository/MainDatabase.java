package lt.vcs.baigiamasis.repository;

import androidx.room.Database;
import androidx.room.TypeConverters;

import lt.vcs.baigiamasis.zaidimukasclasses.Constant;

@Database(
        entities = {Character.class},
        version = Constant.CHARACTER_DATABASE_VERSION,
        exportSchema = false
)
@TypeConverters({Converter.class})
public abstract class MainDatabase {
    public abstract CharacterDao characterDao();
    public abstract EnemyDao enemyDao();
    public abstract ItemDao itemDao();
}
