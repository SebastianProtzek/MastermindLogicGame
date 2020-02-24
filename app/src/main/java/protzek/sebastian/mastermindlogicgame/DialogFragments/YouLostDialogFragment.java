package protzek.sebastian.mastermindlogicgame.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.Objects;

import protzek.sebastian.mastermindlogicgame.Enums.Ball;
import protzek.sebastian.mastermindlogicgame.R;

public class YouLostDialogFragment extends AppCompatDialogFragment {
    private DialogInterface.OnClickListener listener;
    private ArrayList<Integer> masterNumbers;

    public YouLostDialogFragment(ArrayList<Integer> masterNumbers) {
        this.masterNumbers = masterNumbers;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.correct_set, null);
        builder.setView(view)
                .setPositiveButton("Restart", listener)
                .setNegativeButton("Main menu", listener);

        ImageView firstCorrectBallImageView = view.findViewById(R.id.first_correct_ball_image_view);
        ImageView secondCorrectBallImageView = view.findViewById(R.id.second_correct_ball_image_view);
        ImageView thirdCorrectBallImageView = view.findViewById(R.id.third_correct_ball_image_view);
        ImageView fourthCorrectBallImageView = view.findViewById(R.id.fourth_correct_ball_image_view);

        Ball firstBall = Ball.fromBallNumber(masterNumbers.get(0));
        Ball secondBall = Ball.fromBallNumber(masterNumbers.get(1));
        Ball thirdBall = Ball.fromBallNumber(masterNumbers.get(2));
        Ball fourthBall = Ball.fromBallNumber(masterNumbers.get(3));

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
