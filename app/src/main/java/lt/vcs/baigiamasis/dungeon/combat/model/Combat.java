package lt.vcs.baigiamasis.dungeon.combat.model;

import java.util.Random;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.player.model.Player;
import lt.vcs.baigiamasis.enemy.model.Enemy;

public class Combat {

    private final Player player;
    private final Enemy enemy;
    private final int playerWeaponDamage;
    private final int playerArmor;
    private final Random random;

    public Combat(Player player, Enemy enemy, int playerWeaponDamage, int playerArmor) {
        this.player = player;
        this.enemy = enemy;
        this.playerWeaponDamage = playerWeaponDamage;
        this.playerArmor = playerArmor;
        random = new Random();
    }

    public int calculatePlayerDamage(){
        int attackValue = random.nextInt(20) + 1 + player.getStatStr()-10;
        int hit = (attackValue >= enemy.getArmor())? 1 : 0;
        if (attackValue == 20) hit = 2;

        int playerMaxDamage = playerWeaponDamage + player.getStatStr()-10 + Constant.BASE_DAMAGE;

        return hit * random.nextInt(playerMaxDamage+1);
    }

    public int calculatePlayerDamageSpecialAttack(){
        int attackValue = random.nextInt(20) + 1 + player.getStatStr()-10 + player.getStatWis()-10;
        int hit = (attackValue >= enemy.getArmor())? 1 : 0;
        if (attackValue == 20) hit = 2;

        int playerMaxDamage = playerWeaponDamage + player.getStatStr()-10 + Constant.BASE_DAMAGE + player.getStatWis()-8;

        return hit * random.nextInt(playerMaxDamage+1);
    }

    public int calculateEnemyDamage(){
        int attackValue = random.nextInt(20) + 1;
        int hit = (attackValue >= playerArmor)? 1 : 0;
        if (attackValue == 20) hit = 2;

        return hit * random.nextInt(enemy.getMaxDamage()+1);
    }
}
