package lt.vcs.baigiamasis.dungeon.model;


import lt.vcs.baigiamasis.Constant;

public class EncounterTreasure extends Encounter {
    public EncounterTreasure(int id, int encounterReward) {
        super(id);
        super.setEncounterType(Constant.TREASURE);
        super.setEncounterReward(encounterReward);
        super.setActivateButtonText("Enter treasure");

        switch (id) {
            case 7:
                super.setDescriptionText("Goblin / gold treasure (clanking of metal)");
                break;
            case 8:
                super.setDescriptionText("Slime / health potion (bubbling)");
                break;
            default:
        }
    }
}
