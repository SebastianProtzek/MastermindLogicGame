package protzek.sebastian.mastermindlogicgame.mainmenu;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.media.MusicPlayer;

public class OptionsActivity extends AppCompatActivity {
    private NumberPicker turnsNumberPicker;
    private SwitchCompat volumeSwitch;
    private EditText nicknameEditText;
    private SharedPreferences.Editor editor;

    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        nicknameEditText = findViewById(R.id.nickname_edit_text);
        String nickname = prefs.getString(getString(R.string.nickname_key), "no_name");
        nicknameEditText.setText(nickname);
        setNickname();

        int turns = prefs.getInt(getString(R.string.turns_key), 10);
        setTurnsNumberPicker(turns);

        volumeSwitch = findViewById(R.id.volume_switch);
        volumeSwitcher();
        boolean volume = prefs.getBoolean(getString(R.string.volume_key), false);
        volumeSwitch.setChecked(volume);

        editor.apply();
//        TODO: add sounds
        // TODO: add reset button
    }

    private void setNickname() {
        nicknameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                editor.putString(getString(R.string.nickname_key), String.valueOf(nicknameEditText.getText()));
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

    private void volumeSwitcher() {
        volumeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    musicPlayer.getSound(OptionsActivity.this);
                    editor.putBoolean(getString(R.string.volume_key), true);
                }
                if (!isChecked) {
                    if (musicPlayer.getPlayer() != null) {
                        musicPlayer.getPlayer().release();
                        musicPlayer.setPlayer(null);
                    }
                    editor.putBoolean(getString(R.string.volume_key), false);
                }
                editor.apply();
            }
        });
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

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        editor.putString(getString(R.string.language_key), lang);
        editor.putInt(getString(R.string.turns_key), turnsNumberPicker.getValue());
        editor.putBoolean(getString(R.string.volume_key), volumeSwitch.isChecked());
        editor.apply();
        recreate();
    }

    public void backToMainMenu(View view) {
        int turns = turnsNumberPicker.getValue();
        editor.putInt(getString(R.string.turns_key), turns);
        editor.putBoolean(getString(R.string.volume_key), volumeSwitch.isChecked());
        editor.apply();
        setResult(RESULT_OK);
        finish();
    }
}
