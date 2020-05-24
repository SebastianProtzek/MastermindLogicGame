package protzek.sebastian.mastermindlogicgame.mainmenu.options;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.media.MusicPlayer;

public class OptionsManager {
    private Activity context;
    private Resources r;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    public OptionsManager(Activity context) {
        this.context = context;
        r = context.getResources();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
        editor.apply();
    }

    public void setAllOptions(EditText nicknameEditText, NumberPicker turnsNumberPicker,
                              SwitchCompat soundSwitchCompat, SwitchCompat musicSwitchCompat) {
        setNicknameOption(nicknameEditText);
        setTurnsOption(turnsNumberPicker);
        setLanguage(prefs.getString(r.getString(R.string.language_key), Default.LANGUAGE));
        setSoundOption(soundSwitchCompat);
        setMusicOption(musicSwitchCompat);
    }

    private void setNicknameOption(EditText nicknameEditText) {
        String nickname = prefs.getString(r.getString(R.string.player_nickname_key), Default.NICKNAME);
        nicknameEditText.setText(nickname);
    }

    private void setTurnsOption(NumberPicker turnsNumberPicker) {
        int turns = prefs.getInt(r.getString(R.string.turns_key), Default.TURNS);
        turnsNumberPicker.setMinValue(1);
        turnsNumberPicker.setMaxValue(20);
        turnsNumberPicker.setValue(turns);
    }

    public void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        r.updateConfiguration(config, r.getDisplayMetrics());
        editor.putString(r.getString(R.string.language_key), language);
        editor.apply();
    }

    private void setSoundOption(SwitchCompat soundSwitchCompat) {
        boolean soundOn = prefs.getBoolean(r.getString(R.string.sound_key), Default.VOLUME);
        soundSwitchCompat.setChecked(soundOn);
    }

    private void setMusicOption(SwitchCompat musicSwitchCompat) {
        boolean musicOn = prefs.getBoolean(r.getString(R.string.music_key), Default.VOLUME);
        musicSwitchCompat.setChecked(musicOn);
        musicSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    musicPlayer.getMusic(context);
                    editor.putBoolean(r.getString(R.string.music_key), true);
                }
                if (!isChecked) {
                    if (musicPlayer.getPlayer() != null) {
                        musicPlayer.getPlayer().release();
                        musicPlayer.setPlayer(null);
                    }
                    editor.putBoolean(r.getString(R.string.music_key), false);
                }
                editor.apply();
            }
        });
    }

    public void saveOptions(EditText nicknameEditText, NumberPicker turnsNumberPicker,
                            SwitchCompat soundSwitchCompat, SwitchCompat musicSwitchCompat) {
        String nickname = String.valueOf(nicknameEditText.getText());
        editor.putString(r.getString(R.string.player_nickname_key), nickname);
        int turns = turnsNumberPicker.getValue();
        editor.putInt(r.getString(R.string.turns_key), turns);
        boolean sound = soundSwitchCompat.isChecked();
        editor.putBoolean(r.getString(R.string.sound_key), sound);
        boolean music = musicSwitchCompat.isChecked();
        editor.putBoolean(r.getString(R.string.music_key), music);
        editor.apply();
    }

    public void resetOptions() {
        editor.putString(r.getString(R.string.player_nickname_key), Default.NICKNAME);
        editor.putInt(r.getString(R.string.turns_key), Default.TURNS);
        editor.putString(r.getString(R.string.language_key), Default.LANGUAGE);
        editor.putBoolean(r.getString(R.string.sound_key), Default.VOLUME);
        editor.putBoolean(r.getString(R.string.music_key), Default.VOLUME);
        editor.apply();
    }
}
