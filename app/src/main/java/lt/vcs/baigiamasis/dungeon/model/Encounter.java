package lt.vcs.baigiamasis.dungeon.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lt.vcs.baigiamasis.common.Constant;

@Entity(tableName = Constant.ENTITY_ENCOUNTER_TABLE)
public class Encounter {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "Encounter_reward")
    private int encounterReward;
    @ColumnInfo(name = "Encounter_enemy_id")
    private int linkedEnemyId;
    @ColumnInfo(name = "Encounter_type")
    private String encounterType;

    public Encounter(int id) {
        this.id = id;
        this.encounterReward = 0;
        this.linkedEnemyId = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEncounterReward() {
        return encounterReward;
    }

    public void setEncounterReward(int encounterReward) {
        this.encounterReward = encounterReward;
    }

    public int getLinkedEnemyId() {
        return linkedEnemyId;
    }

    public void setLinkedEnemyId(int linkedEnemyId) {
        this.linkedEnemyId = linkedEnemyId;
    }

    public String getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(String encounterType) {
        this.encounterType = encounterType;
    }
}
