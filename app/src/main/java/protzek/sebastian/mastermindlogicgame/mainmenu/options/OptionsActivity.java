package protzek.sebastian.mastermindlogicgame.mainmenu.options;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.media.MusicPlayer;
import protzek.sebastian.mastermindlogicgame.media.SoundBank;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class OptionsActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private NumberPicker turnsNumberPicker;
    private SwitchCompat musicSwitch;
    private SwitchCompat soundSwitch;
    private EditText nicknameEditText;
    private SharedPreferences.Editor editor;
    private SoundPlayer soundPlayer;
    private boolean doublePressed;

    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        coordinatorLayout = findViewById(R.id.options_coordinator_layout);
        soundPlayer = new SoundPlayer(this);
        doublePressed = false;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        editor.apply();

        nicknameEditText = findViewById(R.id.nickname_edit_text);
        String nickname = prefs.getString(getString(R.string.player_nickname_key), Preferences.NICKNAME_DEFAULT);
        setNickname(nickname);

        int turns = prefs.getInt(getString(R.string.turns_key), Preferences.TURNS_DEFAULT);
        setTurnsNumberPicker(turns);

        String language = prefs.getString(getString(R.string.language_key), Preferences.LANGUAGE_DEFAULT);
        setLanguage(language);

        soundSwitch = findViewById(R.id.sound_switch);
        boolean soundOn = prefs.getBoolean(getString(R.string.sound_key), Preferences.VOLUME_DEFAULT);
        soundSwitcher(soundOn);

        musicSwitch = findViewById(R.id.music_switch);
        boolean music = prefs.getBoolean(getString(R.string.music_key), Preferences.VOLUME_DEFAULT);
        musicSwitcher(music);
    }

    private void setNickname(String nickname) {
        nicknameEditText.setText(nickname);
        nicknameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                editor.putString(getString(R.string.player_nickname_key), String.valueOf(nicknameEditText.getText()));
                editor.apply();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void setTurnsNumberPicker(int turns) {
        turnsNumberPicker = findViewById(R.id.turns_picker);
        turnsNumberPicker.setMinValue(1);
        turnsNumberPicker.setMaxValue(20);
        turnsNumberPicker.setValue(turns);
    }

    public void changeLanguage(View view) {
        String lang;
        if (view.equals(view.getRootView().findViewById(R.id.polish_flag_image_view))) {
            lang = "pl";
        } else if (view.equals(view.getRootView().findViewById(R.id.german_flag_image_view))) {
            lang = "de";
        } else {
            lang = "en";
        }

        setLanguage(lang);
        editor.putString(getString(R.string.language_key), lang);
        editor.putInt(getString(R.string.turns_key), turnsNumberPicker.getValue());
        editor.putBoolean(getString(R.string.music_key), musicSwitch.isChecked());
        editor.putBoolean(getString(R.string.sound_key), soundSwitch.isChecked());
        editor.apply();
        recreate();
    }

    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void soundSwitcher(boolean soundOn) {
        soundSwitch.setChecked(soundOn);
    }

    private void musicSwitcher(boolean music) {
        musicSwitch.setChecked(music);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    musicPlayer.getMusic(OptionsActivity.this);
                    editor.putBoolean(getString(R.string.music_key), true);
                }
                if (!isChecked) {
                    if (musicPlayer.getPlayer() != null) {
                        musicPlayer.getPlayer().release();
                        musicPlayer.setPlayer(null);
                    }
                    editor.putBoolean(getString(R.string.music_key), false);
                }
                editor.apply();
            }
        });
    }

    public void backToMainMenu(View view) {
        int turns = turnsNumberPicker.getValue();
        editor.putInt(getString(R.string.turns_key), turns);
        editor.putBoolean(getString(R.string.music_key), musicSwitch.isChecked());
        editor.putBoolean(getString(R.string.sound_key), soundSwitch.isChecked());
        editor.apply();
        setResult(RESULT_OK);
        playSound(SoundBank.PRESSED_BUTTON);
        finish();
    }

    public void resetOptions(View view) {
        if (doublePressed) {
            editor.putString(getString(R.string.player_nickname_key), Preferences.NICKNAME_DEFAULT);
            nicknameEditText.setText(Preferences.NICKNAME_DEFAULT);
            editor.putInt(getString(R.string.turns_key), Preferences.TURNS_DEFAULT);
            editor.putString(getString(R.string.language_key), Preferences.LANGUAGE_DEFAULT);
            setLanguage(Preferences.LANGUAGE_DEFAULT);
            editor.putBoolean(getString(R.string.music_key), Preferences.VOLUME_DEFAULT);
            musicSwitch.setChecked(Preferences.VOLUME_DEFAULT);
            soundSwitch.setChecked(Preferences.VOLUME_DEFAULT);
            editor.apply();
            playSound(SoundBank.START_GAME);
            recreate();
        } else {
            doublePressed = true;
            Snackbar.make(coordinatorLayout, getString(R.string.reset_options_warning), Snackbar.LENGTH_SHORT).show();
            playSound(SoundBank.RESET);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doublePressed = false;
                }
            }, 3000);
        }
    }

    private void playSound(int sound) {
        soundPlayer.playSound(sound);
    }
}
