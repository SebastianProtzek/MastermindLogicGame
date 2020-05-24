package protzek.sebastian.mastermindlogicgame.gameboard.listeners;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Objects;

import protzek.sebastian.mastermindlogicgame.gameboard.SingleTurn;
import protzek.sebastian.mastermindlogicgame.gameboard.enums.Ball;
import protzek.sebastian.mastermindlogicgame.gameboard.enums.EmptySlot;
import protzek.sebastian.mastermindlogicgame.media.Animations;

public class DragListenerDataCatcher {
    private Animations anim = new Animations();
    private ArrayList<Integer> playerSet = new ArrayList<>();

    void actionDrop(View draggedView, View dropView, SingleTurn singleTurn) {

        Ball ball = Ball.fromId(draggedView.getId());
        int draggedBall = Objects.requireNonNull(ball).getImageResource();
        int numberOfBallColor = ball.getBallNumber();
        ImageView imgDrop = dropView.findViewById(dropView.getId());
        imgDrop.setImageResource(draggedBall);
        EmptySlot emptySlot = EmptySlot.fromId(dropView.getId());

        playerSet.set(Objects.requireNonNull(emptySlot).getIndex(), numberOfBallColor);

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
        if (playerSet.size() == 0)
            for (int i = 0; i < 4; i++) {
                playerSet.add(0);
            }
    }

    public void resetPlayerSet() {
        for (int i = 0; i < 4; i++) {
            playerSet.set(i, 0);
        }
    }

    public ArrayList<Integer> getPlayerSet() {
        return playerSet;
    }
}
