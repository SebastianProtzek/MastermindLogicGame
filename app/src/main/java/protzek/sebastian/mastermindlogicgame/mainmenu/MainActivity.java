package protzek.sebastian.mastermindlogicgame.mainmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.dialogfragments.QuitGameInfoDialogFragment;
import protzek.sebastian.mastermindlogicgame.gameboard.GameBoardActivity;
import protzek.sebastian.mastermindlogicgame.mainmenu.howtoplay.HowToPlayActivity;
import protzek.sebastian.mastermindlogicgame.media.MusicPlayer;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private SharedPreferences prefs;
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: find good font for texts
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setLanguage();
        setVolume();
        printGreeting();
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        Intent intent = new Intent(MainActivity.this, GameBoardActivity.class);
        startActivity(intent);
    }

    public void openHowToPlay(View view) {
        Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
        startActivity(intent);
    }

    public void openOptions(View view) {
        Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openHighScores(View view) {
        Intent intent = new Intent(MainActivity.this, HighScoresActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            recreate();
    }

    private void setLanguage() {
        String language = prefs.getString(getString(R.string.language_key), "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void setVolume() {
        boolean volume = prefs.getBoolean(getString(R.string.volume_key), false);
        if (volume) {
            musicPlayer.getSound(MainActivity.this);
        }
    }

    private void printGreeting() {
        Resources res = getResources();
        String defaultNickName = "no_name";
        String nickname = prefs.getString(getString(R.string.nickname_key), defaultNickName);
        String toastMessage;
        if (nickname.equals(defaultNickName)) {
            toastMessage = getString(R.string.random_greeting);
        } else {
            toastMessage = String.format(res.getString(R.string.personal_greeting), nickname);
        }
        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
        ((TextView) ((LinearLayout) toast.getView()).getChildAt(0))
                .setGravity(Gravity.CENTER_HORIZONTAL);
        toast.show();
    }
}
