package protzek.sebastian.mastermindlogicgame.gameboard.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MasterSetGenerator {
    private final int firstNumber = generateNumber();
    private final int secondNumber = generateNumber();
    private final int thirdNumber = generateNumber();
    private final int fourthNumber = generateNumber();

    private ArrayList<Integer> masterSet = new ArrayList<>(Arrays.asList(firstNumber, secondNumber, thirdNumber,
            fourthNumber));

    private int generateNumber() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

    public void resetMasterSet() {
        masterSet.set(0, firstNumber);
        masterSet.set(1, secondNumber);
        masterSet.set(2, thirdNumber);
        masterSet.set(3, fourthNumber);
    }

    public ArrayList<Integer> getMasterSet() {
        return masterSet;
    }
}
