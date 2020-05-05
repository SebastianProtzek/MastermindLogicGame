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
import protzek.sebastian.mastermindlogicgame.dialogfragments.QuitGameInfoDialogFragment;
import protzek.sebastian.mastermindlogicgame.gameboard.GameBoardActivity;
import protzek.sebastian.mastermindlogicgame.mainmenu.highscores.HighScoresActivity;
import protzek.sebastian.mastermindlogicgame.mainmenu.howtoplay.HowToPlayActivity;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.OptionsActivity;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.Preferences;
import protzek.sebastian.mastermindlogicgame.media.Animations;
import protzek.sebastian.mastermindlogicgame.media.MusicPlayer;
import protzek.sebastian.mastermindlogicgame.media.SoundBank;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private Animations anim = new Animations();
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private SoundPlayer soundPlayer;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soundPlayer = new SoundPlayer(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
        anim.zoomIn(optionsButton);
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
        QuitGameInfoDialogFragment dialogFragment = new QuitGameInfoDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), null);
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
        }
    }

    private void setLanguage() {
        String language = prefs.getString(getString(R.string.language_key), Preferences.LANGUAGE_DEFAULT);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
    }

    private void setVolume() {
        boolean music = prefs.getBoolean(getString(R.string.music_key), Preferences.VOLUME_DEFAULT);
        if (music) {
            musicPlayer.getMusic(MainActivity.this);
        }
    }

    private void printGreeting() {
        Resources res = getResources();
        String nickname = prefs.getString(getString(R.string.player_nickname_key), Preferences.NICKNAME_DEFAULT);
        String toastMessage;
        if (nickname.equals(Preferences.NICKNAME_DEFAULT)) {
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
        anim.zoomIn(button);
        Intent intent = new Intent(MainActivity.this, c);
        startActivity(intent);
        playSound(SoundBank.PRESSED_BUTTON);
    }

    private void playSound(int sound) {
        soundPlayer.playSound(sound);
    }
}
