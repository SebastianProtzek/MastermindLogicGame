package protzek.sebastian.mastermindlogicgame.gameboard.listeners;

import android.os.Handler;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.media.Animations;

public class CheckIfButtonController {
    private Animations anim = new Animations();
    private DragListenerDataCatcher dldc;

    public CheckIfButtonController(DragListenerDataCatcher dldc) {
        this.dldc = dldc;
    }

    void checkIfEndTurnPossible(Button button) {
        ArrayList<Integer> playerSet = dldc.getPlayerSet();
        if (playerSet.get(0) != 0
                && playerSet.get(1) != 0
                && playerSet.get(2) != 0
                && playerSet.get(3) != 0) {
            button.setText(R.string.end_turn);
            int color = ContextCompat.getColor(button.getContext(), R.color.start_blue);
            button.setTextColor(color);
            checkIfPlayerEndedTurn(button);
        }
    }

    private void checkIfPlayerEndedTurn(final Button button) {
        anim.zoomIn(button, 1.2f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIfEndTurnPossible(button);
            }
        }, 3000);
    }

    public void checkIfPlayerPressedStart(final Button button, final Animations anim) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String buttonStringValue = button.getText().toString();
                if (buttonStringValue.equals("Start")) {
                    anim.zoomIn(button, 1.2f);
                    checkIfPlayerPressedStart(button, anim);
                }
            }
        }, 3000);
    }
}
