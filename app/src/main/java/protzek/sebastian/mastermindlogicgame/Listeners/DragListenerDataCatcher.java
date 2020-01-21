package protzek.sebastian.mastermindlogicgame.Listeners;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.R;

public class DragListenerDataCatcher {

    private ArrayList<Integer> playerNumbers = new ArrayList<>();
    private int numberOfBallColor = 0;

    void actionDrop(ImageView img, View draggedView, View subjectView, TextView textView) {
        if (draggedView.getId() == R.id.blue_ball) {
            img.setImageResource(R.drawable.ball_blue);
            setNumberOfBallColor(1);
        } else if (draggedView.getId() == R.id.yellow_ball) {
            img.setImageResource(R.drawable.ball_yellow);
            setNumberOfBallColor(2);
        } else if (draggedView.getId() == R.id.red_ball) {
            img.setImageResource(R.drawable.ball_red);
            setNumberOfBallColor(3);
        } else if (draggedView.getId() == R.id.green_ball) {
            img.setImageResource(R.drawable.ball_green);
            setNumberOfBallColor(4);
        } else if (draggedView.getId() == R.id.orange_ball) {
            img.setImageResource(R.drawable.ball_orange);
            setNumberOfBallColor(5);
        } else if (draggedView.getId() == R.id.white_ball) {
            img.setImageResource(R.drawable.ball_white);
            setNumberOfBallColor(6);
        }

        if (subjectView.getId() == R.id.firstBall) {
            textView.setText("hah");
            playerNumbers.set(0, numberOfBallColor);
        } else if (subjectView.getId() == R.id.secondBall) {
            textView.setText("drugi!");
            playerNumbers.set(1, numberOfBallColor);
        } else if (subjectView.getId() == R.id.thirdBall) {
            textView.setText("czeci!");
            playerNumbers.set(2, numberOfBallColor);
        } else if (subjectView.getId() == R.id.fourthBall) {
            textView.setText("wow!");
            playerNumbers.set(3, numberOfBallColor);
        }
    }

    public void setNumberOfBallColor(int numberOfBallColor) {
        this.numberOfBallColor = numberOfBallColor;
    }

    public ArrayList<Integer> getPlayerNumbers() {
        return playerNumbers;
    }

    void addEmptySlots() {
        if(playerNumbers.size() == 0)
            for (int i = 0; i < 4; i++) {
            playerNumbers.add(0);
        }
    }
}
