package protzek.sebastian.mastermindlogicgame.gameboard;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.dialogfragments.DialogFragmentCreator;
import protzek.sebastian.mastermindlogicgame.gameboard.listeners.BallTouchListener;
import protzek.sebastian.mastermindlogicgame.mainmenu.highscores.HighScoresListBuilder;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.Default;
import protzek.sebastian.mastermindlogicgame.media.*;

public class GameBoardActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private Animations anim = new Animations();
    private SharedPreferences prefs;
    private GameBoardActivityHelper helper;
    private HighScoresListBuilder highScoresBuilder;
    private DialogFragmentCreator fragmentCreator;
    private SoundPlayer soundPlayer;
    private Button restartEndTurnButton;
    private TextView turnsTextView;
    private String turnsLeftAsString;
    private int numberOfTurns;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soundPlayer = new SoundPlayer(this);
        setContentView(R.layout.activity_game_board);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        helper = new GameBoardActivityHelper(soundPlayer);
        highScoresBuilder = new HighScoresListBuilder(this, prefs);
        fragmentCreator = new DialogFragmentCreator(this);
        restartEndTurnButton = findViewById(R.id.restart_end_turn_button);
        turnsTextView = findViewById(R.id.turns_text_view);
        RecyclerView recyclerView = findViewById(R.id.game_board_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(helper.getAdapter());
        invokeAllBallListeners();

        numberOfTurns = prefs.getInt(getString(R.string.turns_key), 10);
        helper.setTurnsLeft(numberOfTurns);
        turnsLeftAsString = String.format(getString(R.string.turns_left), (helper.getIndexOfActiveTurn() + 1), numberOfTurns);
        turnsTextView.setText(turnsLeftAsString);
        helper.getController().checkIfPlayerPressedStart(restartEndTurnButton, anim);
    }

    public void reStartOrEndTurn(View view) {
        String buttonStringValue = restartEndTurnButton.getText().toString();
        switch (buttonStringValue) {
            case "Start":
                startGame();
                break;
            case "Restart":
                restartGame();
                break;
            default:
                endTurn();
                if (helper.isPlayerWon()) {
                    youWon();
                } else if (helper.getTurnsLeft() > 0) {
                    prepareNextTurn();
                } else {
                    youLost();
                }
                break;
        }
    }

    private void startGame() {
        helper.createNextTurn();
        restartEndTurnButton.setText(R.string.restart);
        playSound(SoundBank.START_GAME);
    }

    private void restartGame() {
        fragmentCreator.showRestartGameDialogFragment();
    }

    private void endTurn() {
        helper.endTurn();
    }

    private void youWon() {
        fragmentCreator.showYouWonDialogFragment(isNewRecord());
        helper.getDataCatcher().resetPlayerSet();
        restartEndTurnButton.setText(R.string.restart);
        highScoresBuilder.updateHighScores();
        playSound(SoundBank.YOU_WON);
    }

    private void prepareNextTurn() {
        helper.prepareNextTurn();
        restartEndTurnButton.setText(R.string.restart);
        int color = ContextCompat.getColor(restartEndTurnButton.getContext(), R.color.gold);
        restartEndTurnButton.setTextColor(color);
        turnsLeftAsString = String.format(getString(R.string.turns_left), (helper.getIndexOfActiveTurn() + 1), numberOfTurns);
        turnsTextView.setText(turnsLeftAsString);
        scrollView();
        anim.blink(turnsTextView);
        playSound(SoundBank.NEXT_TURN);
    }

    private void youLost() {
        helper.getGenerator().resetMasterSet();
        fragmentCreator.showYouLostDialogFragment(helper.getGenerator().getMasterSet());
        restartEndTurnButton.setText(R.string.restart);
        playSound(SoundBank.YOU_LOST);
    }

    private void scrollView() {
        final NestedScrollView scrollView = findViewById(R.id.scroll_view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private boolean isNewRecord() {
        int record = prefs.getInt(getString(R.string.top1_score_key), Default.SCORE);
        int playerScore = (helper.getIndexOfActiveTurn() + 1) * 100 + numberOfTurns;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getString(R.string.player_score_key), playerScore);
        editor.apply();
        return playerScore < record;
    }

    public void backToMainMenu(View view) {
        fragmentCreator.showExitToMainMenuDialogFragment();
        playSound(SoundBank.ARE_YOU_SURE);
    }

    private void playSound(int sound) {
        soundPlayer.playSound(sound);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void invokeAllBallListeners() {
        ImageView blueBallImageView = findViewById(R.id.blue_ball_image_view);
        ImageView yellowBallImageView = findViewById(R.id.yellow_ball_image_view);
        ImageView redBallImageView = findViewById(R.id.red_ball_image_view);
        ImageView greenBallImageView = findViewById(R.id.green_ball_image_view);
        ImageView orangeBallImageView = findViewById(R.id.orange_ball_image_view);
        ImageView whiteBallImageView = findViewById(R.id.white_ball_image_view);

        BallTouchListener btl = new BallTouchListener();
        ArrayList<ImageView> balls = new ArrayList<>(Arrays.asList(blueBallImageView, yellowBallImageView,
                redBallImageView, greenBallImageView, orangeBallImageView, whiteBallImageView));
        for (ImageView imageView : balls) {
            imageView.setOnTouchListener(btl);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int dialogSwitch = fragmentCreator.getDialogSwitch();
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                playSound(SoundBank.PRESSED_BUTTON);
                finish();
                if (dialogSwitch == 1 || dialogSwitch == 2) {
                    startActivity(getIntent());
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                playSound(SoundBank.PRESSED_BUTTON);
                if (dialogSwitch == 2)
                    finish();
                break;
        }
    }
}
