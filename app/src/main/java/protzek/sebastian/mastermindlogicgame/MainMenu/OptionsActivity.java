package protzek.sebastian.mastermindlogicgame.MainMenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import protzek.sebastian.mastermindlogicgame.R;

public class OptionsActivity extends AppCompatActivity {
    private NumberPicker numberPickerTurns;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int turns = prefs.getInt(getString(R.string.turns_key), 10);

        numberPickerTurns = findViewById(R.id.turns_picker);
        numberPickerTurns.setMinValue(1);
        numberPickerTurns.setMaxValue(20);
        numberPickerTurns.setValue(turns);
    }

    public void backToMainMenu(View view) {
        int turns = numberPickerTurns.getValue();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putInt(getString(R.string.turns_key), turns);
        editor.apply();
        finish();
    }
}
