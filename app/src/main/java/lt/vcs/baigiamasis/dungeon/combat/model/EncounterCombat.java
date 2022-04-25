package lt.vcs.baigiamasis.dungeon.combat.model;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.model.Encounter;

public class EncounterCombat extends Encounter {

    public EncounterCombat(int id, int linkedEnemyId) {
        super(id);
        super.setLinkedEnemyId(linkedEnemyId);
        super.setEncounterType(Constant.COMBAT);
    }
}

//public void generateRandomEnemy(Enemy enemy){
//enemy.initializeEnemy();
//} o metode kuris launchina sita tai bus dungeon.generateRandomEnemy(enemyList.get(random.nextInt(enemyList.size)))