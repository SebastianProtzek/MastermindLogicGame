package protzek.sebastian.mastermindlogicgame.MainMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import protzek.sebastian.mastermindlogicgame.R;

public class CreditsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    public void checkTheCode(View view) {
        String url = "https://github.com/SebastianProtzek/MastermindLogicGame";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public void backToMainMenu(View view) {
        finish();
    }
}
