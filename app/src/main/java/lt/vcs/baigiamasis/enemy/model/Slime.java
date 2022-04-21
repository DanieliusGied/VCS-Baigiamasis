package lt.vcs.baigiamasis.enemy.model;

public class Slime extends Enemy{

    public Slime(int id) {
        super(id);
        super.setName("Slime");
        super.setHealth(1);
        super.setArmor(20);
        super.setGoldDrop(2);
        super.setMaxDamage(3);
    }
}
