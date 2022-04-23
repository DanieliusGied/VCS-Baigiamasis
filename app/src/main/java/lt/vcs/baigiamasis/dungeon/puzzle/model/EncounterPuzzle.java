package lt.vcs.baigiamasis.dungeon.puzzle.model;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.model.Encounter;

public class EncounterPuzzle extends Encounter {
    public EncounterPuzzle(int id) {
        super(id);
        super.setEncounterType(Constant.PUZZLE);
        super.setActivateButtonText("Enter puzzle");

        switch (id) {
            case 5:
                super.setDescriptionText("Skeleton / puzzle (clanking of bones)");
                break;
            case 6:
                super.setDescriptionText("Giant rat / puzzle ");
                break;
            default:
        }
    }

    //    @Override
//    public void initialize(){
//        System.out.println("ROOM " + dungeon.dungeonProgress);
//        System.out.println("You see an ominous door before you. You see ancient lettering and a number dial on the lock.");
//        System.out.println("1 - Configure the number dial");
//        System.out.println("ELSE - Leave the dungeon");
//
//        switch (scannerSelect()){
//            case 1:
//                solvePuzzle(generatePuzzles());
//                dungeon.dungeonProgress++;
//                dungeon.roomList.add(roomCombat);
////                dungeon.roomList.add(roomTreasure);
//                System.out.println(dungeon.roomList);
//                dungeon.generateRoom();
//                break;
//            default:
//                dungeon.dungeonProgress = 0;
//                System.out.println("You decide to leave the dungeon.");
//                break;
//        }
//    }
//
//    public String generatePuzzles(){
//        String puzzle1 = "2 + 2";
//        String puzzle2 = "1 + 3";
//        String puzzle3 = "11 - 7";
//        String puzzle4 = "1 * 4";
//        String puzzle5 = "8 / 2";
//
//        ArrayList<String> puzzles = new ArrayList<>();
//        puzzles.add(puzzle1);
//        puzzles.add(puzzle2);
//        puzzles.add(puzzle3);
//        puzzles.add(puzzle4);
//        puzzles.add(puzzle5);
//
//        return puzzles.get(random.nextInt(puzzles.size()));
//    }
//
//    public void solvePuzzle(String puzzle){
//        System.out.println("The ancient lettering etched into the stone door reads " + puzzle);
//        System.out.println("What would you like to enter in the number dial?");
//
//        boolean puzzleSolved = false;
//        while (puzzleSolved == false) {
//
//            switch (scannerSelect()) {
//                case 4:
//                    System.out.println("The lock makes a satisfying click and the door opens.");
//                    puzzleSolved = true;
//                    break;
//                default:
//                    player.health--;
//                    System.out.println("The room fills with poison and " + player.name + " takes 1 damage! " + player.name + " has " + player.health + " health remaining!");
//                    if (player.health <= 0) player.death();
//                    break;
//            }
//        }
//    }
}
