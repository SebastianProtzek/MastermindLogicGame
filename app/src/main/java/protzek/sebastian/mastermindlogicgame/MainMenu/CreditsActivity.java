package protzek.sebastian.mastermindlogicgame.MainMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import protzek.sebastian.mastermindlogicgame.R;

public class CreditsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        RatingBar topRatingBar = findViewById(R.id.top_rating_bar);
        RatingBar bottomRatingBar = findViewById(R.id.bottom_rating_bar);
        wannaRate(topRatingBar);
        wannaRate(bottomRatingBar);
    }

    public void checkTheCode(View view) {
        String url = "https://github.com/SebastianProtzek/MastermindLogicGame";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public void wannaRate(RatingBar ratingBar) {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.wanna_rate), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backToMainMenu(View view) {
        finish();
    }
}
