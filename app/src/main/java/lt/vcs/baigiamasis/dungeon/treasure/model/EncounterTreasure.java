package lt.vcs.baigiamasis.dungeon.treasure.model;


import java.util.Random;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.model.Encounter;

public class EncounterTreasure extends Encounter {

    Random random;

    public EncounterTreasure(int id, int encounterReward) {
        super(id);
        super.setEncounterType(Constant.TREASURE);
        super.setEncounterReward(encounterReward);
        super.setActivateButtonText("Enter treasure");

        switch (id) {
            case 7:
                super.setDescriptionText("Goblin / item treasure (clanking of metal)");
                break;
            case 8:
                super.setDescriptionText("Slime / health potion (bubbling)");
                break;
            default:
        }

        random = new Random();
    }
}
