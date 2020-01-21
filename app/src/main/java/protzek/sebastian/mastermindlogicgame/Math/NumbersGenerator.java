package protzek.sebastian.mastermindlogicgame.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NumbersGenerator {
    private final int firstNumber = generateNumber();
    private final int secondNumber = generateNumber();
    private final int thirdNumber = generateNumber();
    private final int fourthNumber = generateNumber();

    private int numberOfTurns = 10;

    private List<Integer> masterNumbers = new ArrayList<>(Arrays.asList(firstNumber, secondNumber, thirdNumber,
            fourthNumber));

    private int generateNumber() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

    public void setDefaultNumbers() {
        masterNumbers.set(0, firstNumber);
        masterNumbers.set(1, secondNumber);
        masterNumbers.set(2, thirdNumber);
        masterNumbers.set(3, fourthNumber);
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public List<Integer> getMasterNumbers() {
        return masterNumbers;
    }
}
