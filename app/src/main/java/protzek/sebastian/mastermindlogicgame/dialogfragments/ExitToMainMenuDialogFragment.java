package protzek.sebastian.mastermindlogicgame.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import protzek.sebastian.mastermindlogicgame.R;

public class ExitToMainMenuDialogFragment extends AppCompatDialogFragment {

    private DialogInterface.OnClickListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        // TODO: make a layout for it
        builder.setTitle(R.string.go_back_to_main_menu_question)
                .setMessage(R.string.progress_wont_be_saved)
                .setPositiveButton(R.string.main_menu, listener)
                .setNegativeButton(R.string.cancel, listener);
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
}