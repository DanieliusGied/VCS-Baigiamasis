//package lt.vcs.baigiamasis.zaidimukasclasses;
//
//import java.util.InputMismatchException;
//
//import static lt.vcs.zaidimukas.Main.*;
//
//public class RoomCombat extends Room {
//
//    @Override
//    public void initialize(){
//        System.out.println("ROOM " + dungeon.dungeonProgress);
//        System.out.println("Looks like there's a fight ahead! What would you like to do?");
//        System.out.println("1 - Fight");
//        System.out.println("ELSE - Leave the dungeon");
//
//        switch (scannerSelect()){
//            case 1:
//                Enemy randomEnemy = dungeon.enemyList.get(random.nextInt(dungeon.enemyList.size()));
//                if (generateRandomEnemy(randomEnemy) == true) {
//                    dungeon.dungeonProgress++;
////                    dungeon.roomList.add(roomTreasure);
//                    dungeon.roomList.add(roomPuzzle);
//                    dungeon.generateRoom();
//                } else {
//                    dungeon.dungeonProgress = 0;
//                    return;
//                }
//                break;
//            default:
//                dungeon.dungeonProgress = 0;
//                break;
//        }
//    }
//
//    public boolean generateRandomEnemy(Enemy enemy){
//        enemy.initializeEnemy();
//        return combat.fight(player, enemy);
//    }
//}
//
////public void generateRandomEnemy(Enemy enemy){
////enemy.initializeEnemy();
////} o metode kuris launchina sita tai bus dungeon.generateRandomEnemy(enemyList.get(random.nextInt(enemyList.size)))