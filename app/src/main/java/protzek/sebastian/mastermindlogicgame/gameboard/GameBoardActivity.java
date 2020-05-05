package protzek.sebastian.mastermindlogicgame.gameboard;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.dialogfragments.ExitToMainMenuDialogFragment;
import protzek.sebastian.mastermindlogicgame.dialogfragments.RestartGameDialogFragment;
import protzek.sebastian.mastermindlogicgame.dialogfragments.YouLostDialogFragment;
import protzek.sebastian.mastermindlogicgame.dialogfragments.YouWonDialogFragment;
import protzek.sebastian.mastermindlogicgame.gameboard.listeners.BallTouchListener;
import protzek.sebastian.mastermindlogicgame.gameboard.listeners.DragListenerDataCatcher;
import protzek.sebastian.mastermindlogicgame.mainmenu.highscores.HighScoresListBuilder;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.Preferences;
import protzek.sebastian.mastermindlogicgame.gameboard.math.Comparator;
import protzek.sebastian.mastermindlogicgame.gameboard.math.NumbersGenerator;
import protzek.sebastian.mastermindlogicgame.gameboard.math.WonOrNot;
import protzek.sebastian.mastermindlogicgame.media.Animations;
import protzek.sebastian.mastermindlogicgame.media.SoundBank;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class GameBoardActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private GameBoardAdapter adapter;
    private SharedPreferences prefs;
    private HighScoresListBuilder hslb;
    private SoundPlayer soundPlayer;
    private Button restartEndTurnButton;
    private TextView turnsTextView;
    private ArrayList<Integer> masterNumbers;
    private ArrayList<SingleTurn> game;

    private String turnsLeftAsString;
    private int dialogSwitch, numberOfTurns, turnsLeft, indexOfActiveTurn;
    private boolean didPlayerWin;

    private Animations anim = new Animations();
    private DragListenerDataCatcher dldc = new DragListenerDataCatcher();
    private NumbersGenerator ng = new NumbersGenerator();
    private Comparator com = new Comparator();
    private WonOrNot won = new WonOrNot();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO: create view model/subclass for it?
        // TODO: any animations more?
        super.onCreate(savedInstanceState);
        soundPlayer = new SoundPlayer(this);
        setContentView(R.layout.activity_game_board);
        restartEndTurnButton = findViewById(R.id.restart_end_turn_button);
        turnsTextView = findViewById(R.id.turns_text_view);
        RecyclerView recyclerView = findViewById(R.id.game_board_recycler_view);
        invokeAllBallListeners();

        masterNumbers = ng.getMasterNumbers();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        hslb = new HighScoresListBuilder(this, prefs);
        indexOfActiveTurn = 0;
        numberOfTurns = prefs.getInt(getString(R.string.turns_key), 10);
        turnsLeft = numberOfTurns;
        turnsLeftAsString = String.format(getString(R.string.turns_left), (indexOfActiveTurn + 1), numberOfTurns);
        turnsTextView.setText(turnsLeftAsString);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        game = new ArrayList<>();
        adapter = new GameBoardAdapter(dldc, soundPlayer, game, indexOfActiveTurn);
        recyclerView.setAdapter(adapter);
        View view = recyclerView.getChildAt(0);
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
                if (didPlayerWin) {
                    youWon();
                } else if (turnsLeft > 0) {
                    prepareNextTurn();
                } else {
                    youLost();
                }
                break;
        }
    }

    private void startGame() {
        createNextTurn(game);
        adapter.notifyItemChanged(game.size());
        restartEndTurnButton.setText(R.string.restart);
        playSound(SoundBank.START_GAME);
    }

    private void restartGame() {
        dialogSwitch = 1;
        RestartGameDialogFragment restartGameDialogFragment = new RestartGameDialogFragment();
        restartGameDialogFragment.show(getSupportFragmentManager(), null);
    }

    private void endTurn() {
        turnsLeft--;
        ArrayList<Integer> playerGuess = dldc.getPlayerNumbers();
        ArrayList<Integer> guessResult = com.compareNumbers(masterNumbers, playerGuess);
        didPlayerWin = won.checkIfWon(guessResult);
        SingleTurn updatedActiveTurn = showResult(guessResult, game.get(indexOfActiveTurn));
        game.set(indexOfActiveTurn, updatedActiveTurn);
        adapter.notifyDataSetChanged();
    }

    private void youWon() {
        dialogSwitch = 3;
        YouWonDialogFragment youWonDialogFragment = new YouWonDialogFragment();
        youWonDialogFragment.setNewRecord(isNewRecord());
        youWonDialogFragment.show(getSupportFragmentManager(), null);
        restartEndTurnButton.setText(R.string.restart);
        hslb.updateHighScores();
        playSound(SoundBank.YOU_WON);
    }

    private void prepareNextTurn() {
        ng.resetDefaultNumbers();
        dldc.resetPlayerNumbers();
        restartEndTurnButton.setText(R.string.restart);
        int color = ContextCompat.getColor(restartEndTurnButton.getContext(), R.color.gold);
        restartEndTurnButton.setTextColor(color);
        createNextTurn(game);
        indexOfActiveTurn = game.size() - 1;
        turnsLeftAsString = String.format(getString(R.string.turns_left), (indexOfActiveTurn + 1), numberOfTurns);
        turnsTextView.setText(turnsLeftAsString);
        anim.blink(turnsTextView);
        adapter.setIndexOfActiveTurn(indexOfActiveTurn);
        scrollView();
        playSound(SoundBank.NEXT_TURN);
    }

    private void youLost() {
        ng.resetDefaultNumbers();
        dialogSwitch = 3;
        YouLostDialogFragment youLostDialogFragment = new YouLostDialogFragment(masterNumbers);
        youLostDialogFragment.show(getSupportFragmentManager(), null);
        restartEndTurnButton.setText(R.string.restart);
        playSound(SoundBank.YOU_LOST);
    }

    private SingleTurn showResult(ArrayList<Integer> guessResult, SingleTurn currentTurn) {
        int counterRightPosition = guessResult.get(0);
        int counterRightNumber = guessResult.get(1);
        ArrayList<Integer> scorePins = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (counterRightPosition > 0) {
                scorePins.add(R.drawable.empty_circle_fire);
                counterRightPosition--;
            } else if (counterRightNumber > 0) {
                scorePins.add(R.drawable.empty_circle_thunder);
                counterRightNumber--;
            } else {
                scorePins.add(R.drawable.empty_circle_golden);
            }
        }
        adapter.getItemId(indexOfActiveTurn);
        currentTurn.setFirstScorePin(scorePins.get(0));
        currentTurn.setSecondScorePin(scorePins.get(1));
        currentTurn.setThirdScorePin(scorePins.get(2));
        currentTurn.setFourthScorePin(scorePins.get(3));
        return currentTurn;
    }

    private void createNextTurn(ArrayList<SingleTurn> list) {
        final int ECG = R.drawable.empty_circle_golden;
        SingleTurn nextTurn = new SingleTurn(
                ECG, ECG, ECG, ECG, ECG, ECG, ECG, ECG);
        list.add((nextTurn));
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
        int record = prefs.getInt(getString(R.string.top1_score_key), Preferences.SCORE_DEFAULT);
        int playerScore = (indexOfActiveTurn + 1) * 100 + numberOfTurns;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getString(R.string.player_score_key), playerScore);
        editor.apply();

        return playerScore < record;
    }

    public void backToMainMenu(View view) {
        dialogSwitch = 2;
        ExitToMainMenuDialogFragment dialogFragment = new ExitToMainMenuDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), null);
        playSound(SoundBank.ARE_YOU_SURE);
    }

    private void playSound(int sound) {
            soundPlayer.playSound(sound);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void invokeAllBallListeners() {
        ImageView blueBallImageView = findViewById(R.id.blue_ball_image_view);
        ImageView yellowBallImageView = findViewById(R.id.yellow_ball_image_view);
        ImageView redBallImageView = findViewById(R.id.red_ball_image_view);
        ImageView greenBallImageView = findViewById(R.id.green_ball_image_view);
        ImageView orangeBallImageView = findViewById(R.id.orange_ball_image_view);
        ImageView whiteBallImageView = findViewById(R.id.white_ball_image_view);

        BallTouchListener btl = new BallTouchListener();
        blueBallImageView.setOnTouchListener(btl);
        yellowBallImageView.setOnTouchListener(btl);
        redBallImageView.setOnTouchListener(btl);
        greenBallImageView.setOnTouchListener(btl);
        orangeBallImageView.setOnTouchListener(btl);
        whiteBallImageView.setOnTouchListener(btl);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                playSound(SoundBank.PRESSED_BUTTON);
                finish();
                if (dialogSwitch == 1 || dialogSwitch == 3) {
                    startActivity(getIntent());
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                playSound(SoundBank.PRESSED_BUTTON);
                if (dialogSwitch == 3)
                    finish();
                break;
        }
    }
}
