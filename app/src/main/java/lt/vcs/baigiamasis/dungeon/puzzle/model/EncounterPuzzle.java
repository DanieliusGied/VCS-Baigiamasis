package lt.vcs.baigiamasis.dungeon.puzzle.model;

import lt.vcs.baigiamasis.common.Constant;
import lt.vcs.baigiamasis.dungeon.model.Encounter;

public class EncounterPuzzle extends Encounter {
    public EncounterPuzzle(int id) {
        super(id);
        super.setEncounterType(Constant.PUZZLE);
    }
}
