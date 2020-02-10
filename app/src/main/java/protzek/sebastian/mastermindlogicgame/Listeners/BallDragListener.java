package protzek.sebastian.mastermindlogicgame.Listeners;

import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.SingleTurn;

public class BallDragListener implements View.OnDragListener {
    private int indexOfActiveTurn;
    private int position;
    private DragListenerDataCatcher dldc;
    private SingleTurn singleTurn;

    public BallDragListener(int indexOfActiveTurn, int position, DragListenerDataCatcher dldc, SingleTurn singleTurn) {
        this.indexOfActiveTurn = indexOfActiveTurn;
        this.position = position;
        this.dldc = dldc;
        this.singleTurn = singleTurn;
    }

    @Override
    public boolean onDrag(View dropView, DragEvent event) {
        if (indexOfActiveTurn == position) {
            int action = event.getAction();
            final View draggedView = (View) event.getLocalState();
            Button restartEndTurnButton = dropView.getRootView().findViewById(R.id.restart_end_turn_button);
            ImageView img = dropView.findViewById(dropView.getId());
            dldc.addEmptySlots();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                case DragEvent.ACTION_DRAG_EXITED:
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                case DragEvent.ACTION_DROP:
                    dldc.actionDrop(draggedView, dropView, img, singleTurn);
                    dldc.checkIfEndTurnPossible(restartEndTurnButton);
                    break;
            }
        }
        return true;
    }
}
