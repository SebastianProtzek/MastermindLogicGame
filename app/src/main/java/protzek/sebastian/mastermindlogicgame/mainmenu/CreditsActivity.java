package protzek.sebastian.mastermindlogicgame.mainmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.Preferences;
import protzek.sebastian.mastermindlogicgame.media.SoundBank;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class CreditsActivity extends AppCompatActivity {
    private SoundPlayer soundPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soundPlayer = new SoundPlayer(this);
        setContentView(R.layout.activity_credits);
        RatingBar topRatingBar = findViewById(R.id.top_rating_bar);
        RatingBar bottomRatingBar = findViewById(R.id.bottom_rating_bar);
        wannaRate(topRatingBar);
        wannaRate(bottomRatingBar);
    }

    public void checkTheCode(View view) {
        String url = "https://github.com/SebastianProtzek/MastermindLogicGame";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        playSound(SoundBank.PRESSED_BUTTON);
    }

    public void wannaRate(RatingBar ratingBar) {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.wanna_rate), Toast.LENGTH_SHORT).show();
                playSound(SoundBank.ARE_YOU_SURE);
            }
        });
    }

    public void backToMainMenu(View view) {
        playSound(SoundBank.PRESSED_BUTTON);
        finish();
    }

    private void playSound(int sound) {
            soundPlayer.playSound(sound);
    }
}
