package protzek.sebastian.mastermindlogicgame.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import protzek.sebastian.mastermindlogicgame.R;

public class YouWonDialogFragment extends AppCompatDialogFragment {
    private DialogInterface.OnClickListener listener;
    private boolean newRecord;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final ViewGroup nullParent = null;
        View view = inflater.inflate(R.layout.fragment_you_won, nullParent);
        builder.setView(view)
                .setPositiveButton(R.string.play_again, listener)
                .setNegativeButton(R.string.main_menu, listener);

        TextView winMessageTextView = view.findViewById(R.id.win_message_text_view);

        if (newRecord) {
            winMessageTextView.setText(getString(R.string.new_record));
            winMessageTextView.setTextColor(getResources().getColor(R.color.gold));
        }

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogInterface.OnClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement DialogInterface.OnClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }
}
