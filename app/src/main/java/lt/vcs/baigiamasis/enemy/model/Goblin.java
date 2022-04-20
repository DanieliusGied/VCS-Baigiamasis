package lt.vcs.baigiamasis.enemy.model;

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
