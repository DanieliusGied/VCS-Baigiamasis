package lt.vcs.baigiamasis.dungeon.combat.model;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.model.Encounter;

public class EncounterCombat extends Encounter {

    public EncounterCombat(int id, int linkedEnemyId) {
        super(id);
        super.setLinkedEnemyId(linkedEnemyId);
        super.setEncounterType(Constant.COMBAT);
        super.setActivateButtonText("Enter combat");

        switch (id) {
            case 1:
                super.setDescriptionText("Goblin / gold treasure (clanking of metal)");
                break;
            case 2:
                super.setDescriptionText("Skeleton / puzzle (clanking of bones)");
                break;
            case 3:
                super.setDescriptionText("Giant rat / puzzle ");
                break;
            case 4:
                super.setDescriptionText("Slime / health potion (bubbling)");
                break;
            default:
        }
    }
}

//public void generateRandomEnemy(Enemy enemy){
//enemy.initializeEnemy();
//} o metode kuris launchina sita tai bus dungeon.generateRandomEnemy(enemyList.get(random.nextInt(enemyList.size)))