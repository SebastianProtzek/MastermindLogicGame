package protzek.sebastian.mastermindlogicgame.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.Objects;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.gameboard.enums.Ball;

public class YouLostDialogFragment extends AppCompatDialogFragment {
    private DialogInterface.OnClickListener listener;
    private ArrayList<Integer> masterSet;

    public YouLostDialogFragment(ArrayList<Integer> masterSet) {
        this.masterSet = masterSet;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final ViewGroup nullParent = null;
        View view = inflater.inflate(R.layout.fragment_you_lost, nullParent);
        builder.setView(view)
                .setPositiveButton(R.string.restart, listener)
                .setNegativeButton(R.string.main_menu, listener);

        ImageView firstCorrectBallImageView = view.findViewById(R.id.first_correct_ball_image_view);
        ImageView secondCorrectBallImageView = view.findViewById(R.id.second_correct_ball_image_view);
        ImageView thirdCorrectBallImageView = view.findViewById(R.id.third_correct_ball_image_view);
        ImageView fourthCorrectBallImageView = view.findViewById(R.id.fourth_correct_ball_image_view);

        Ball firstBall = Ball.fromBallNumber(masterSet.get(0));
        Ball secondBall = Ball.fromBallNumber(masterSet.get(1));
        Ball thirdBall = Ball.fromBallNumber(masterSet.get(2));
        Ball fourthBall = Ball.fromBallNumber(masterSet.get(3));

        firstCorrectBallImageView.setImageResource(Objects.requireNonNull(firstBall).getImageResource());
        secondCorrectBallImageView.setImageResource(Objects.requireNonNull(secondBall).getImageResource());
        thirdCorrectBallImageView.setImageResource(Objects.requireNonNull(thirdBall).getImageResource());
        fourthCorrectBallImageView.setImageResource(Objects.requireNonNull(fourthBall).getImageResource());

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
