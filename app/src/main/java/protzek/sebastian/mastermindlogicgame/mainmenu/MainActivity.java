package protzek.sebastian.mastermindlogicgame.mainmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.dialogfragments.DialogFragmentCreator;
import protzek.sebastian.mastermindlogicgame.gameboard.GameBoardActivity;
import protzek.sebastian.mastermindlogicgame.mainmenu.highscores.HighScoresActivity;
import protzek.sebastian.mastermindlogicgame.mainmenu.howtoplay.HowToPlayActivity;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.OptionsActivity;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.Default;
import protzek.sebastian.mastermindlogicgame.media.Animations;
import protzek.sebastian.mastermindlogicgame.media.MusicPlayer;
import protzek.sebastian.mastermindlogicgame.media.SoundBank;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private Animations anim = new Animations();
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private SoundPlayer soundPlayer;
    private SharedPreferences prefs;
    private DialogFragmentCreator dfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Ask if create bundles? An object with SoundPlayer, dldc, etc.
        // TODO: Known issue: background image strange white frame on top and left side
        // TODO: Known issue: music doesn't stop when app in background - onPause() override? appState? Passing musicPlayer everywhere?
        // TODO: publish app
        super.onCreate(savedInstanceState);
        soundPlayer = new SoundPlayer(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        dfc = new DialogFragmentCreator(this);
        setLanguage();
        setVolume();
        printGreeting();
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        startActivityWithSoundAndAnimation(GameBoardActivity.class, R.id.start_button);
    }

    public void openHowToPlay(View view) {
        startActivityWithSoundAndAnimation(HowToPlayActivity.class, R.id.how_to_play_button);
    }

    public void openOptions(View view) {
        Button optionsButton = findViewById(R.id.option_button);
        anim.zoomIn(optionsButton, 12f);
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivityForResult(intent, 0);
        playSound(SoundBank.PRESSED_BUTTON);
    }

    public void openHighScores(View view) {
        startActivityWithSoundAndAnimation(HighScoresActivity.class, R.id.high_scores_button);
    }

    public void openCredits(View view) {
        startActivityWithSoundAndAnimation(CreditsActivity.class, R.id.credits_button);
    }

    public void quitGame(View view) {
        dfc.showQuitGameInfoDialogFragment();
        playSound(SoundBank.QUIT_GAME);
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
        if (resultCode == RESULT_OK) {
            setContentView(R.layout.activity_main);
            soundPlayer = new SoundPlayer(this);
        }
    }

    private void setLanguage() {
        String language = prefs.getString(getString(R.string.language_key), Default.LANGUAGE);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
    }

    private void setVolume() {
        boolean music = prefs.getBoolean(getString(R.string.music_key), Default.VOLUME);
        if (music) {
            musicPlayer.getMusic(MainActivity.this);
        }
    }

    private void printGreeting() {
        Resources res = getResources();
        String nickname = prefs.getString(getString(R.string.player_nickname_key), Default.NICKNAME);
        String toastMessage;
        if (nickname.equals(Default.NICKNAME)) {
            toastMessage = getString(R.string.random_greeting);
        } else {
            toastMessage = String.format(res.getString(R.string.personal_greeting), nickname);
        }
        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
        ((TextView) ((LinearLayout) toast.getView()).getChildAt(0))
                .setGravity(Gravity.CENTER_HORIZONTAL);
        toast.show();
    }

    private void startActivityWithSoundAndAnimation(final Class c, int buttonId) {
        Button button = findViewById(buttonId);
        anim.zoomIn(button, 12f);
        Intent intent = new Intent(MainActivity.this, c);
        startActivity(intent);
        playSound(SoundBank.PRESSED_BUTTON);
    }

    private void playSound(int sound) {
        soundPlayer.playSound(sound);
    }
}
