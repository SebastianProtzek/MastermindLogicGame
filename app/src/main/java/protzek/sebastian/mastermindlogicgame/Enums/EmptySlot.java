package protzek.sebastian.mastermindlogicgame.Enums;

import protzek.sebastian.mastermindlogicgame.R;

public enum EmptySlot {
    FIRST(0, R.id.firstEmptySlot),
    SECOND(1, R.id.secondEmptySlot),
    THIRD(2, R.id.thirdEmptySlot),
    FOURTH(3, R.id.fourthEmptySlot);

    private final int index;
    private final int id;

    EmptySlot(int index, int id) {
        this.index = index;
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public int getId() {
        return id;
    }

    public static EmptySlot fromId(int id) {
        EmptySlot[] values = values();
        for (EmptySlot emptySlot : values) {
            if (emptySlot.getId() == id)
                return emptySlot;
        }
        return null;
    }
}
