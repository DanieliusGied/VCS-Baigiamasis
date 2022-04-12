package lt.vcs.baigiamasis.zaidimukasclasses;

import static lt.vcs.baigiamasis.MainActivity.random;

public class Goblin extends Enemy{

    @Override
    public void initializeEnemy(){
        super.setName("Goblin");
        super.setHealth(3);
        super.setArmor(7);
        super.setGoldDrop(2);
        super.setMaxDamage(2);
    }
}
