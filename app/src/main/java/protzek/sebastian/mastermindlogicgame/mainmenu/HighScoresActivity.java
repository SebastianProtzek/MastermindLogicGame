package protzek.sebastian.mastermindlogicgame.mainmenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import protzek.sebastian.mastermindlogicgame.R;

public class HighScoresActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int test = prefs.getInt(getString(R.string.player_score_key), 1000);
        TextView tv = findViewById(R.id.test);
        tv.setText(String.valueOf(test));

    }
}
