package protzek.sebastian.mastermindlogicgame.listeners;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Objects;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.enums.Ball;
import protzek.sebastian.mastermindlogicgame.enums.EmptySlot;
import protzek.sebastian.mastermindlogicgame.gameboard.SingleTurn;

public class DragListenerDataCatcher {

    private ArrayList<Integer> playerNumbers = new ArrayList<>();

    void actionDrop(View draggedView, View dropView, ImageView img, SingleTurn singleTurn) {

        Ball ball = Ball.fromId(draggedView.getId());
        int draggedBall = Objects.requireNonNull(ball).getImageResource();
        int numberOfBallColor = ball.getBallNumber();
        img.setImageResource(draggedBall);
        EmptySlot emptySlot = EmptySlot.fromId(dropView.getId());
        playerNumbers.set(Objects.requireNonNull(emptySlot).getIndex(), numberOfBallColor);

        switch (emptySlot.getIndex()) {
            case 0:
                singleTurn.setFirstBall(draggedBall);
                break;
            case 1:
                singleTurn.setSecondBall(draggedBall);
                break;
            case 2:
                singleTurn.setThirdBall(draggedBall);
                break;
            case 3:
                singleTurn.setFourthBall(draggedBall);
                break;
        }
    }

    void addEmptySlots() {
        if (playerNumbers.size() == 0)
            for (int i = 0; i < 4; i++) {
                playerNumbers.add(0);
            }
    }

    void checkIfEndTurnPossible(Button endTurnButton) {
        if (playerNumbers.get(0) != 0
                && playerNumbers.get(1) != 0
                && playerNumbers.get(2) != 0
                && playerNumbers.get(3) != 0) {
            endTurnButton.setText(R.string.end_turn);
            int color = ContextCompat.getColor(endTurnButton.getContext(), R.color.start_blue);
            endTurnButton.setTextColor(color);
        }
    }

    public ArrayList<Integer> getPlayerNumbers() {
        return playerNumbers;
    }

    public void resetPlayerNumbers() {
        for (int i = 0; i < 4; i++) {
            playerNumbers.set(i, 0);
        }
    }
}
