package protzek.sebastian.mastermindlogicgame.dialogfragments;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class DialogFragmentCreator {
    private FragmentManager fm;
    private int dialogSwitch;

    public DialogFragmentCreator(Activity activity) {
        fm = ((FragmentActivity) activity).getSupportFragmentManager();
    }

    public void showRestartGameDialogFragment() {
        dialogSwitch = 1;
        RestartGameDialogFragment restartGameDialogFragment = new RestartGameDialogFragment();
        restartGameDialogFragment.show(fm, null);
    }

    public void showYouWonDialogFragment(boolean newRecord) {
        dialogSwitch = 2;
        YouWonDialogFragment youWonDialogFragment = new YouWonDialogFragment();
        youWonDialogFragment.setNewRecord(newRecord);
        youWonDialogFragment.show(fm, null);
    }

    public void showYouLostDialogFragment(ArrayList<Integer> masterSet) {
        dialogSwitch = 2;
        YouLostDialogFragment youLostDialogFragment = new YouLostDialogFragment(masterSet);
        youLostDialogFragment.show(fm, null);
    }

    public void showExitToMainMenuDialogFragment() {
        ExitToMainMenuDialogFragment dialogFragment = new ExitToMainMenuDialogFragment();
        dialogFragment.show(fm, null);
    }

    public void showQuitGameInfoDialogFragment() {
        QuitGameInfoDialogFragment dialogFragment = new QuitGameInfoDialogFragment();
        dialogFragment.show(fm, null);
    }

    public int getDialogSwitch() {
        return dialogSwitch;
    }
}
