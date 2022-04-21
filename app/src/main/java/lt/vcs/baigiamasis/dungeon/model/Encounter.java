package lt.vcs.baigiamasis.dungeon.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Encounter {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "Encounter_reward")
    private int encounterReward;
    @ColumnInfo(name = "Encounter_enemy_id")
    private int linkedEnemyId;
    @ColumnInfo(name = "Encounter_type")
    private String encounterType;
    @ColumnInfo(name = "Encounter_description_text")
    private String descriptionText;
    @ColumnInfo(name = "Encounter_activate_button_text")
    private String activateButtonText;

    public Encounter(int id) {
        this.id = id;
        this.encounterReward = 0;
        this.linkedEnemyId = 0;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getActivateButtonText() {
        return activateButtonText;
    }

    public void setActivateButtonText(String activateButtonText) {
        this.activateButtonText = activateButtonText;
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
