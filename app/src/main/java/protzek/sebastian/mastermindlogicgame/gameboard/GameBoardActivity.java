package protzek.sebastian.mastermindlogicgame.gameboard;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.dialogfragments.ExitToMainMenuDialogFragment;
import protzek.sebastian.mastermindlogicgame.dialogfragments.RestartGameDialogFragment;
import protzek.sebastian.mastermindlogicgame.dialogfragments.YouLostDialogFragment;
import protzek.sebastian.mastermindlogicgame.dialogfragments.YouWonDialogFragment;
import protzek.sebastian.mastermindlogicgame.listeners.BallTouchListener;
import protzek.sebastian.mastermindlogicgame.listeners.DragListenerDataCatcher;
import protzek.sebastian.mastermindlogicgame.math.Comparator;
import protzek.sebastian.mastermindlogicgame.math.NumbersGenerator;
import protzek.sebastian.mastermindlogicgame.math.WonOrNot;

public class GameBoardActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private Resources res;
    private Button restartEndTurnButton;
    private TextView turnsTextView;
    private String turnsLeftAsString;
    private int dialogSwitch, numberOfTurns, turnsLeft, indexOfActiveTurn;
    private boolean didPlayerWin;
    private ArrayList<Integer> masterNumbers;
    private ArrayList<SingleTurn> game;
    private GameBoardAdapter adapter;
    private SharedPreferences prefs;

    private DragListenerDataCatcher dldc = new DragListenerDataCatcher();
    private NumbersGenerator ng = new NumbersGenerator();
    private Comparator com = new Comparator();
    private WonOrNot won = new WonOrNot();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        invokeAllBallListeners();
        restartEndTurnButton = findViewById(R.id.restart_end_turn_button);
        turnsTextView = findViewById(R.id.turns_text_view);

        masterNumbers = ng.getMasterNumbers();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        indexOfActiveTurn = 0;
        numberOfTurns = prefs.getInt(getString(R.string.turns_key), 10);
        turnsLeft = numberOfTurns;

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        game = new ArrayList<>();
        adapter = new GameBoardAdapter(game, dldc, indexOfActiveTurn);
        recyclerView.setAdapter(adapter);

        res = getResources();
        turnsLeftAsString = String.format(res.getString(R.string.turns_left), (indexOfActiveTurn + 1), numberOfTurns);
        turnsTextView.setText(turnsLeftAsString);
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

    public void backToMainMenu(View view) {
        dialogSwitch = 2;
        ExitToMainMenuDialogFragment dialogFragment = new ExitToMainMenuDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    private void startGame() {
        createNextTurn(game);
        restartEndTurnButton.setText(R.string.restart);
        adapter.notifyItemChanged(game.size());
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
        youWonDialogFragment.show(getSupportFragmentManager(), null);
        getPlayerScore();
        // TODO: add sound of winning
        restartEndTurnButton.setText(R.string.restart);
    }

    private void prepareNextTurn() {
        ng.resetDefaultNumbers();
        dldc.resetPlayerNumbers();
        restartEndTurnButton.setText(R.string.restart);
        int color = ContextCompat.getColor(restartEndTurnButton.getContext(), R.color.gold);
        restartEndTurnButton.setTextColor(color);
        createNextTurn(game);
        indexOfActiveTurn = game.size() - 1;
        turnsLeftAsString = String.format(res.getString(R.string.turns_left), (indexOfActiveTurn + 1), numberOfTurns);
        turnsTextView.setText(turnsLeftAsString);
        adapter.setIndexOfActiveTurn(indexOfActiveTurn);
        scrollView();
    }

    private void youLost() {
        ng.resetDefaultNumbers();
        dialogSwitch = 3;
        YouLostDialogFragment youLostDialogFragment = new YouLostDialogFragment(masterNumbers);
        youLostDialogFragment.show(getSupportFragmentManager(), null);
        // TODO: add sound of losing
        restartEndTurnButton.setText(R.string.restart);
    }

    private SingleTurn showResult(ArrayList<Integer> guessResult, SingleTurn currentTurn) {
        int counterRightPosition = guessResult.get(0);
        int counterRightNumber = guessResult.get(1);
        ArrayList<Integer> scorePins = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (counterRightPosition > 0) {
                scorePins.add(R.drawable.empty_circle_fire);
                counterRightPosition = counterRightPosition - 1;
            } else if (counterRightNumber > 0) {
                scorePins.add(R.drawable.empty_circle_thunder);
                counterRightNumber = counterRightNumber - 1;
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

    private void getPlayerScore() {
        int playerScore = (indexOfActiveTurn + 1) * 100 + numberOfTurns;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getString(R.string.player_score_key), playerScore);
        editor.apply();
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
                finish();
                if (dialogSwitch == 1 || dialogSwitch == 3) {
                    startActivity(getIntent());
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                if (dialogSwitch == 3)
                    finish();
                break;
        }
    }
}
