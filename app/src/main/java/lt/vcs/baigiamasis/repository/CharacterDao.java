package lt.vcs.baigiamasis.repository;

import androidx.room.*;

import java.util.List;

import lt.vcs.baigiamasis.character.model.Character;
import lt.vcs.baigiamasis.Constant;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM " + Constant.ENTITY_CHARACTER_TABLE)
    List<Character> getAll();

    @Query("SELECT * FROM " + Constant.ENTITY_CHARACTER_TABLE + " WHERE id =:id")
    Character getItem(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacters(List<Character> characters);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(Character character);

    @Delete
    void deleteCharacter(Character character);

    @Delete
    void deleteCharacter(List<Character> characters);

    @Query("DELETE FROM " + Constant.ENTITY_CHARACTER_TABLE + " WHERE id =:id")
    void deleteItem(int id);

    @Query("SELECT MAX(id) FROM " + Constant.ENTITY_CHARACTER_TABLE)
    int returnMaxID();
}
