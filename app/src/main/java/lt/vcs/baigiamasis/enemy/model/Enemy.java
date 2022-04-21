package lt.vcs.baigiamasis.enemy.model;

import static lt.vcs.baigiamasis.MainActivity.random;

import androidx.room.*;

import lt.vcs.baigiamasis.Constant;

@Entity(tableName = Constant.ENTITY_ENEMY_TABLE)
public class Enemy {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "enemy_name")
    private String name;
    @ColumnInfo(name = "enemy_health")
    private int health;
    @ColumnInfo(name = "enemy_armor")
    private int armor;
    @ColumnInfo(name = "enemy_goldDrop")
    private int goldDrop;
    @ColumnInfo(name = "enemy_max_damage")
    private int maxDamage;
    // create a bunch of enemies that extend this class

    @Ignore
    private int damage;

    public Enemy(int id) {
        this.id = id;
    }

    public int calculateDamage(){
        this.damage = random.nextInt(maxDamage);
        return this.damage;
    }

//    public void giveLoot(java.lang.Character player){
//        player.gold += goldDrop;
//        System.out.println(player.name + " has received " + this.goldDrop + " gold!");
//    }

    //give exp

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getGoldDrop() {
        return goldDrop;
    }

    public void setGoldDrop(int goldDrop) {
        this.goldDrop = goldDrop;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }
}
