package protzek.sebastian.mastermindlogicgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import protzek.sebastian.mastermindlogicgame.DialogFragments.QuitGameInfoDialogFragment;
import protzek.sebastian.mastermindlogicgame.MainMenu.CreditsActivity;
import protzek.sebastian.mastermindlogicgame.MainMenu.OptionsActivity;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        Intent intent = new Intent(MainActivity.this, GameBoardActivity.class);
        startActivity(intent);
    }

    public void openCredits(View view) {
        Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
        startActivity(intent);
    }

    public void quitGame(View view) {
        QuitGameInfoDialogFragment dialogFragment = new QuitGameInfoDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    public void openOptions(View view) {
        Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                finish();
                System.exit(0);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }
}
