package lt.vcs.baigiamasis.player.model;

import androidx.room.*;

import lt.vcs.baigiamasis.Constant;

@Entity(tableName = Constant.ENTITY_PLAYER_TABLE)
public class Player {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "character_name")
    private String name;
    @ColumnInfo(name = "character_level")
    private int level;
    @ColumnInfo(name = "character_str")
    private int statStr;
    @ColumnInfo(name = "character_con")
    private int statCon;
    @ColumnInfo(name = "character_dex")
    private int statDex;
    @ColumnInfo(name = "character_wis")
    private int statWis;
    @ColumnInfo(name = "character_max_health")
    private int maxHealth;
    @ColumnInfo(name = "character_current_health")
    private int currentHealth;
    @ColumnInfo(name = "character_max_mana")
    private int maxMana;
    @ColumnInfo(name = "character_current_mana")
    private int currentMana;
    @ColumnInfo(name = "character_gold")
    private int gold;
    @ColumnInfo(name = "character_armor")
    private int armor;
    @ColumnInfo(name = "character_current_xp")
    private int currentXP;
    @ColumnInfo(name = "character_xp_to_lvl")
    private int xpToLevel;
    @ColumnInfo(name = "character_level_up_available")
    private boolean isLeveledUp;
    @ColumnInfo(name = "character_level_up_points")
    private int levelUpPoints;

    @Ignore
    private int damage;

    @Ignore
    public Player(int id, String name){
        this.name = name;
        this.id = id;

        level = 1;

        this.statStr = 10;
        this.statCon = 10;
        this.statDex = 10;
        this.statWis = 10;

        this.armor = 5 + this.statDex - 10;

        calculateMaxHP();
        calculateMaxMP();
        calculateCurrentHP();
        calculateCurrentMP();
        calculateXPToLevel();

        this.gold = 10;
        this.currentXP = 0;
        this.isLeveledUp = false;
        this.levelUpPoints = 0;
    }

    public Player(int id, String name, int level, int statStr, int statCon, int statDex, int statWis,
                  int maxHealth, int currentHealth, int maxMana, int currentMana, int gold, int armor,
                  int currentXP, int xpToLevel, boolean isLeveledUp, int levelUpPoints) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.statStr = statStr;
        this.statCon = statCon;
        this.statDex = statDex;
        this.statWis = statWis;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.maxMana = maxMana;
        this.currentMana = currentMana;
        this.gold = gold;
        this.armor = armor;
        this.currentXP = currentXP;
        this.xpToLevel = xpToLevel;
        this.isLeveledUp = isLeveledUp;
        this.levelUpPoints = levelUpPoints;
    }

    public void calculateMaxHP(){
        this.maxHealth = 3 + this.level * 2 + (this.statCon - 10);
    }

    public void calculateCurrentHP(){
        this.currentHealth = this.maxHealth;
    }

    public void calculateMaxMP(){
        this.maxMana = 3 + this.level * 2 + (this.statWis - 10);
    }

    public void calculateCurrentMP(){
        this.currentMana = this.maxMana;
    }

    public void calculateXPToLevel(){
        switch(this.level){
            case 1:
                this.xpToLevel = 3;
                break;
            case 2:
                this.xpToLevel = 5;
                break;
            case 3:
                this.xpToLevel = 8;
                break;
            case 4:
                this.xpToLevel = 12;
                break;
            case 5:
                this.xpToLevel = 17;
                break;
            default:
                this.xpToLevel = 999;
        }
    }

    public void levelUp(){
        calculateMaxHP();
        calculateMaxMP();
        calculateCurrentHP();
        calculateCurrentMP();

        if (this.levelUpPoints == 0) isLeveledUp = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getArmor() {
        return armor;
    }

    public int getGold() {
        return gold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getStatStr() {
        return statStr;
    }

    public void setStatStr(int statStr) {
        this.statStr = statStr;
    }

    public int getStatCon() {
        return statCon;
    }

    public void setStatCon(int statCon) {
        this.statCon = statCon;
    }

    public int getStatDex() {
        return statDex;
    }

    public void setStatDex(int statDex) {
        this.statDex = statDex;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public void setCurrentXP(int currentXP) {
        this.currentXP = currentXP;
    }

    public int getXpToLevel() {
        return xpToLevel;
    }

    public int getStatWis() {
        return statWis;
    }

    public void setStatWis(int statWis) {
        this.statWis = statWis;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public boolean isLeveledUp() {
        return isLeveledUp;
    }

    public void setLeveledUp(boolean leveledUp) {
        isLeveledUp = leveledUp;
    }

    public int getLevelUpPoints() {
        return levelUpPoints;
    }

    public void setLevelUpPoints(int levelUpPoints) {
        this.levelUpPoints = levelUpPoints;
    }

    @Override
    public String toString() {
        return id +
                ". " + name +
                ", Lvl " + level;
    }
}
