package lt.vcs.baigiamasis.enemy.model;

public class GiantRat extends Enemy{

    public GiantRat(int id) {
        super(id);
        super.setName("Giant Rat");
        super.setHealth(5);
        super.setArmor(6);
        super.setGoldDrop(1);
        super.setMaxDamage(3);
    }
}
