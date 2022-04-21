package lt.vcs.baigiamasis.enemy.model;

public class Skeleton extends Enemy{

    public Skeleton(int id) {
        super(id);
        super.setName("Skeleton");
        super.setHealth(2);
        super.setArmor(12);
        super.setGoldDrop(3);
        super.setMaxDamage(2);
    }
}
