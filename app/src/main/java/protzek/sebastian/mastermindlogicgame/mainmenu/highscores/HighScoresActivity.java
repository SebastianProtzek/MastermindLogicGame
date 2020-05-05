package protzek.sebastian.mastermindlogicgame.mainmenu.highscores;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.Preferences;
import protzek.sebastian.mastermindlogicgame.media.SoundBank;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class HighScoresActivity extends AppCompatActivity {
    private HighScoresListBuilder hslb;
    private CoordinatorLayout coordinatorLayout;
    private SoundPlayer soundPlayer;
    private boolean doublePressed;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        soundPlayer = new SoundPlayer(this);
        setContentView(R.layout.activity_high_scores);
        coordinatorLayout = findViewById(R.id.high_scores_coordinator_layout);

        RecyclerView recyclerView = findViewById(R.id.high_scores_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hslb = new HighScoresListBuilder(this, prefs);
        ArrayList<HighScore> highScores = hslb.createHighScoresList();
        HighScoresAdapter highScoresAdapter = new HighScoresAdapter(highScores);
        recyclerView.setAdapter(highScoresAdapter);
    }

    public void resetHighScores(View view) {
        if (doublePressed) {
            hslb.resetHighScores();
            recreate();
        } else {
            doublePressed = true;
            Snackbar.make(coordinatorLayout, R.string.reset_high_scores_warning, Snackbar.LENGTH_SHORT).show();
            playSound(SoundBank.RESET);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doublePressed = false;
                }
            }, 3000);
        }
    }

    public void info(View view) {
        Snackbar.make(coordinatorLayout, R.string.info_high_scores, Snackbar.LENGTH_LONG).show();
        playSound(SoundBank.INFO_BUTTON);
    }

    public void backToMainMenu(View view) {
        playSound(SoundBank.PRESSED_BUTTON);
        finish();
    }

    private void playSound(int sound) {
            soundPlayer.playSound(sound);
    }
}
