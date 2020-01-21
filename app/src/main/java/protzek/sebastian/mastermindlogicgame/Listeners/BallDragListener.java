package protzek.sebastian.mastermindlogicgame.Listeners;

import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import protzek.sebastian.mastermindlogicgame.R;

public class BallDragListener implements View.OnDragListener {


    @Override
    public boolean onDrag(View subjectView, DragEvent event) {
        int action = event.getAction();
        final View draggedView = (View) event.getLocalState();
//        ImageView test = view.findViewById(view.getId());
        TextView textView = subjectView.getRootView().findViewById(R.id.turns_text_view);
        ImageView img = subjectView.findViewById(subjectView.getId());
        DragListenerDataCatcher dlh = new DragListenerDataCatcher();
        dlh.addEmptySlots();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
            case DragEvent.ACTION_DRAG_EXITED:
            case DragEvent.ACTION_DRAG_ENTERED:
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            case DragEvent.ACTION_DROP:
                dlh.actionDrop(img, draggedView, subjectView, textView);
                break;
        }
        return true;
    }

}
