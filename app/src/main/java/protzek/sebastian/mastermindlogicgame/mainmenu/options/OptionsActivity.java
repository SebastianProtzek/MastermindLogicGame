package protzek.sebastian.mastermindlogicgame.mainmenu.options;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.media.SoundBank;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class OptionsActivity extends AppCompatActivity {

    private OptionsManager manager;
    private SoundPlayer soundPlayer;
    private CoordinatorLayout coordinatorLayout;
    private EditText nicknameEditText;
    private NumberPicker turnsNumberPicker;
    private SwitchCompat soundSwitch;
    private SwitchCompat musicSwitch;
    private boolean doublePressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        coordinatorLayout = findViewById(R.id.options_coordinator_layout);
        soundPlayer = new SoundPlayer(this);
        doublePressed = false;

        nicknameEditText = findViewById(R.id.nickname_edit_text);
        turnsNumberPicker = findViewById(R.id.turns_picker);
        soundSwitch = findViewById(R.id.sound_switch);
        musicSwitch = findViewById(R.id.music_switch);

        manager = new OptionsManager(this);
        manager.setAllOptions(nicknameEditText, turnsNumberPicker, soundSwitch, musicSwitch);
    }

    public void changeLanguage(View view) {
        String language;
        if (view.equals(view.getRootView().findViewById(R.id.polish_flag_image_view))) {
            language = "pl";
        } else if (view.equals(view.getRootView().findViewById(R.id.german_flag_image_view))) {
            language = "de";
        } else {
            language = "en";
        }

        manager.saveOptions(nicknameEditText, turnsNumberPicker, soundSwitch, musicSwitch);
        manager.setLanguage(language);
        recreate();
        manager.setAllOptions(nicknameEditText, turnsNumberPicker, soundSwitch, musicSwitch);
    }

    public void backToMainMenu(View view) {
        manager.saveOptions(nicknameEditText, turnsNumberPicker, soundSwitch, musicSwitch);
        setResult(RESULT_OK);
        playSound(SoundBank.PRESSED_BUTTON);
        finish();
    }

    public void resetOptions(View view) {
        if (doublePressed) {
            manager.resetOptions();
            playSound(SoundBank.START_GAME);
            recreate();
            manager.setAllOptions(nicknameEditText, turnsNumberPicker, soundSwitch, musicSwitch);
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
