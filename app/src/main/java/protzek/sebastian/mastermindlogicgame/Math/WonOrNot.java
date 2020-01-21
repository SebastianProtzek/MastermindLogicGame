package protzek.sebastian.mastermindlogicgame.Math;

import java.util.List;

public class WonOrNot {
    public boolean checkIfWon(List<Integer> guessResult) {
        int rightNumbers = guessResult.get(0);
        int requiredToWin = 4;
        return rightNumbers == requiredToWin;
    }
}
