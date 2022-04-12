package lt.vcs.baigiamasis.repository;

import androidx.room.Database;

import lt.vcs.baigiamasis.zaidimukasclasses.Constant;

@Database(
        entities = {Character.class},
        version = Constant.CHARACTER_DATABASE_VERSION,
        exportSchema = false
)
public abstract class MainDatabase {
    public abstract CharacterDao characterDao();
    public abstract EnemyDao enemyDao();
}
