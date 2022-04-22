package lt.vcs.baigiamasis.combat.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lt.vcs.baigiamasis.Constant;

@Entity(tableName = Constant.ENTITY_GRAVEYARD_TABLE)
public class Graveyard {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "graveyard_player_id")
    private int playerID;
    @ColumnInfo(name = "graveyard_player_name")
    private String playerName;
    @ColumnInfo(name = "graveyard_player_level")
    private int playerLevel;

    public Graveyard(int playerID, String playerName, int playerLevel) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerLevel = playerLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }
}
