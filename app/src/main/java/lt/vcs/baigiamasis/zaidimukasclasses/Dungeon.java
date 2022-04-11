package lt.vcs.baigiamasis.zaidimukasclasses;

import java.util.ArrayList;
import java.util.InputMismatchException;

import static lt.vcs.zaidimukas.Main.*;

public class Dungeon {
    public ArrayList<Room> roomList;
    public ArrayList<Enemy> enemyList;
    int dungeonProgress;

    public void initialize(){
        this.dungeonProgress = 1;

        roomList = new ArrayList<Room>();
        enemyList = new ArrayList<Enemy>();

        roomList.add(roomPuzzle);
//        roomList.add(roomTreasure);
        roomList.add(roomCombat);
//        roomList.add(roomBoss);

        enemyList.add(goblin);

        generateRoom();
    }

    public void generateRoom(){
        pageBreak();
        if (dungeonProgress < 5) {
            Room randomRoom = this.roomList.get(random.nextInt(this.roomList.size()));
            randomRoom.initialize();
        } else roomBoss.initialize();
    }
}

// make a dungeon level
// initialize enemy based on dungeon level
// create a hashmap of enemy objects and think of a way to "randomEnemyFromTheMap.initializeEnemy()"
// make puzzle rooms and combat rooms, only launch combat.fight(player, enemy) in combat rooms
// create a hashmap of rooms and use the same method/same type of solution to randomize rooms.
// enter dungeon method launches an exploration method which allows you to 1-go to the next room, 2-leave.
    // going to the next room launches a generate room method (pick random room from a map and randomRoom.initialize);
// for enemies create a threat variable and re-generate if the threat isnt ok.

//random index = random.nextInt(roomList.size);
//random entry from a list == roomList.get(random.nextInt(roomList.size));

//public void generateRandomRoom(Room room){
    //room.initializeRoom();
//} o metode kuris launchina sita tai bus dungeon.generateRandomRoom(roomList.get(random.nextInt(roomList.size)))

//public void generateRandomEnemy(Enemy enemy){
    //enemy.initializeEnemy();
//} o metode kuris launchina sita tai bus dungeon.generateRandomEnemy(enemyList.get(random.nextInt(enemyList.size)))
// isvis geriau pasidaryt enemyThreat ir kiekvienam roome pasidaryt roomThreat. Tada:
// Enemy randomEnemy = enemyList.get(random.nextInt(enemyList.size))
// If (enemy.enemyThreat > room.roomThreat) ...