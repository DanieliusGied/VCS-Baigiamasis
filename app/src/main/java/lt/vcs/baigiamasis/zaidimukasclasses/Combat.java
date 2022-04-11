package lt.vcs.baigiamasis.zaidimukasclasses;

import java.util.InputMismatchException;

import static lt.vcs.zaidimukas.Main.*;

public class Combat {

    public int checkIfPlayerHits(java.lang.Character player, Enemy enemy) {
        int attackAccuracy = random.nextInt(20) + (player.stats[0] - 10);
        if (attackAccuracy >= enemy.armor) {
            return player.calculateDamage();
        } else {
            return 0;
        }
    }

    public int checkIfEnemyHits(java.lang.Character player, Enemy enemy) {
        int attackAccuracy = random.nextInt(20);
        if (attackAccuracy >= player.armor) {
            return enemy.calculateDamage();
        } else {
            return 0;
        }
    }

    public int chooseCombatAction(){
        System.out.println("Choose an action:");
        System.out.println("1 - Attack");
        System.out.println("ELSE - Flee");
        int action = scannerSelect();
        return action;
    }

    public boolean fight(java.lang.Character player, Enemy enemy){
        while (player.health > 0 && enemy.health > 0) {
            int turnCheck = 1;

            while (turnCheck == 1) {
                switch (chooseCombatAction()) {
                    case 1: //ATTACK option
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        int playerDamage = checkIfPlayerHits(player, enemy);
                        System.out.print(playerDamage > 0 ? "╔ Attack succesful! " + player.name + " dealt " + playerDamage + " damage to " + enemy.name + "! " : "╔ " + player.name + " missed! ");
                        enemy.health -= playerDamage;
                        System.out.println(enemy.name + " has " + enemy.health + " health remaining!");
                        turnCheck = 2;
                        break;
                    default: //FLEE option
                        System.out.println(player.name + " ran away!");
                        dungeon.dungeonProgress = 0;
                        turnCheck = 3;
                        break;
                }

                if (enemy.health <= 0) turnCheck = 0;

                while (turnCheck == 2){
                    int enemyDamage = checkIfEnemyHits(player, enemy);
                    System.out.print(enemyDamage > 0 ? "╚ " + enemy.name + " dealt " + enemyDamage + " to " + player.name + "! " : "╚ " + enemy.name + " missed! ");
                    player.health -= enemyDamage;
                    System.out.println(player.name + " has " + player.health + " health remaining!");
                    turnCheck = 1;
                    if (player.health <= 0) turnCheck = 0;
            }

                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");

                if (turnCheck == 3) return false;

            }
        }
        if (enemy.health <= 0) {
            System.out.println(enemy.name + " was defeated!");
            enemy.giveLoot(player);
            return true;
        } else {
            System.out.println(player.name + " was defeated!");
            player.death();
            return false;
        }


    }
}
