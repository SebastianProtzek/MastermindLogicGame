package protzek.sebastian.mastermindlogicgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import protzek.sebastian.mastermindlogicgame.DialogFragments.QuitGameInfoDialogFragment;
import protzek.sebastian.mastermindlogicgame.MainMenu.CreditsActivity;
import protzek.sebastian.mastermindlogicgame.MainMenu.HowToPlay.HowToPlayActivity;
import protzek.sebastian.mastermindlogicgame.MainMenu.OptionsActivity;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private SharedPreferences prefs;
    private Intent optionsIntent;
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setLanguage();
        setVolume();
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
        if (optionsIntent == null) {
            optionsIntent = new Intent(MainActivity.this, OptionsActivity.class);
        }
        startActivityForResult(optionsIntent, 0);
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
}
