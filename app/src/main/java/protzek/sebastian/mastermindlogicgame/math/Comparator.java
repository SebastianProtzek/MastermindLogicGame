package protzek.sebastian.mastermindlogicgame.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Comparator {
    public ArrayList<Integer> compareNumbers(ArrayList<Integer> master, ArrayList<Integer> player) {
        int counterRightPosition = 0;
        for (int i = 0; i < master.size(); i++) {
            if (master.get(i).equals(player.get(i))) {
                counterRightPosition++;
                master.set(i, 0);
                player.set(i, -1);
            }
        }
        int counterRightNumber = counterRightNumber(master, player);
        return new ArrayList<>(Arrays.asList(counterRightPosition, counterRightNumber));
    }

    private int counterRightNumber(List<Integer> masterEdited, List<Integer> playerEdited) {
        int counterRightNumber = 0;
        for (int i = 0; i < masterEdited.size(); i++) {
            for (int j = 0; j < playerEdited.size(); j++) {
                if (masterEdited.get(i).equals(playerEdited.get(j))) {
                    counterRightNumber++;
                    masterEdited.set(i, 0);
                    playerEdited.set(j, -1);
                }
            }
        }
        return counterRightNumber;
    }
}