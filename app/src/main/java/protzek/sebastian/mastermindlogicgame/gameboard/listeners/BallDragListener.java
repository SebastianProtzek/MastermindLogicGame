package protzek.sebastian.mastermindlogicgame.gameboard.listeners;

import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.gameboard.SingleTurn;
import protzek.sebastian.mastermindlogicgame.media.SoundBank;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class BallDragListener implements View.OnDragListener {
    private CheckIfButtonController controller;
    private DragListenerDataCatcher dldc;
    private SoundPlayer soundPlayer;
    private SingleTurn singleTurn;
    private int indexOfActiveTurn;
    private int position;


    public BallDragListener(DragListenerDataCatcher dldc, SoundPlayer soundPlayer, SingleTurn singleTurn, int indexOfActiveTurn, int position) {
        this.dldc = dldc;
        this.soundPlayer = soundPlayer;
        this.singleTurn = singleTurn;
        this.indexOfActiveTurn = indexOfActiveTurn;
        this.position = position;

        controller = new CheckIfButtonController(dldc);
    }

    @Override
    public boolean onDrag(View dropView, DragEvent event) {
        if (indexOfActiveTurn == position) {
            int action = event.getAction();
            final View draggedView = (View) event.getLocalState();
            Button restartEndTurnButton = dropView.getRootView().findViewById(R.id.restart_end_turn_button);
            NestedScrollView scrollView = dropView.getRootView().findViewById(R.id.scroll_view);
            ImageView img = dropView.findViewById(dropView.getId());
            dldc.addEmptySlots();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                case DragEvent.ACTION_DRAG_EXITED:
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                case DragEvent.ACTION_DROP:
                    dldc.actionDrop(draggedView, dropView, singleTurn);
                    controller.checkIfEndTurnPossible(restartEndTurnButton);
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    soundPlayer.playSound(SoundBank.DROP_BALL);
                    break;
            }
        }
        return true;
    }
}
