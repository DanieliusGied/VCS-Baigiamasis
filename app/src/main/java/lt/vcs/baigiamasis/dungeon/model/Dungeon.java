package lt.vcs.baigiamasis.dungeon.model;

import androidx.room.*;

import lt.vcs.baigiamasis.common.Constant;

@Entity(tableName = Constant.ENTITY_DUNGEON_TABLE)
public class Dungeon {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "dungeon_player_id")
    private int characterId;
    @ColumnInfo(name = "dungeon_progress")
    private int dungeonProgress;
    @ColumnInfo(name = "dungeon_encounter_id")
    private Integer encounterId;
    @ColumnInfo(name = "dungeon_skips_remaining")
    private int skipsRemaining;
    @ColumnInfo(name = "dungeon_encounter_skipped")
    private boolean isSkipped;
    @ColumnInfo(name = "dungeon_encounter_flee")
    private boolean didFlee;

    public Dungeon(int characterId, int dungeonProgress, int skipsRemaining, boolean isSkipped, boolean didFlee) {
        this.characterId = characterId;
        this.dungeonProgress = dungeonProgress;
        this.skipsRemaining = skipsRemaining;
        this.isSkipped = isSkipped;
        this.didFlee = didFlee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public int getDungeonProgress() {
        return dungeonProgress;
    }

    public void setDungeonProgress(int dungeonProgress) {
        this.dungeonProgress = dungeonProgress;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public int getSkipsRemaining() {
        return skipsRemaining;
    }

    public void setSkipsRemaining(int skipsRemaining) {
        this.skipsRemaining = skipsRemaining;
    }

    public boolean isSkipped() {
        return isSkipped;
    }

    public void setSkipped(boolean skipped) {
        isSkipped = skipped;
    }

    public boolean isDidFlee() {
        return didFlee;
    }

    public void setDidFlee(boolean didFlee) {
        this.didFlee = didFlee;
    }
}