package protzek.sebastian.mastermindlogicgame;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import protzek.sebastian.mastermindlogicgame.DialogFragments.ExitToMainMenuDialogFragment;
import protzek.sebastian.mastermindlogicgame.DialogFragments.RestartGameDialogFragment;
import protzek.sebastian.mastermindlogicgame.Listeners.BallTouchListener;
import protzek.sebastian.mastermindlogicgame.Listeners.DragListenerDataCatcher;
import protzek.sebastian.mastermindlogicgame.Math.Comparator;
import protzek.sebastian.mastermindlogicgame.Math.NumbersGenerator;
import protzek.sebastian.mastermindlogicgame.Math.WonOrNot;

public class GameBoardActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private Resources res;
    private Button restartEndTurnButton;
    private TextView turnsTextView;
    private String turnsLeftAsString;
    private int dialogSwitch;
    private int turnsLeft;
    private int indexOfActiveTurn;
    private List<Integer> masterNumbers;
    private ArrayList<SingleTurn> game;
    private GameBoardAdapter adapter;

    private DragListenerDataCatcher dldc = new DragListenerDataCatcher();
    private NumbersGenerator ng = new NumbersGenerator();
    private Comparator com = new Comparator();
    private WonOrNot won = new WonOrNot();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        invokeAllBallViewsAndTouchListeners();
        restartEndTurnButton = findViewById(R.id.restart_end_turn_button);
        turnsTextView = findViewById(R.id.turns_text_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        indexOfActiveTurn = 0;
        game = new ArrayList<>();
        adapter = new GameBoardAdapter(game, dldc, indexOfActiveTurn);
        recyclerView.setAdapter(adapter);

        masterNumbers = ng.getMasterNumbers();
        turnsLeft = ng.getNumberOfTurns();
        res = getResources();
        turnsLeftAsString = String.format(res.getString(R.string.turns_left), turnsLeft);
        turnsTextView.setText(turnsLeftAsString);
    }


    public void restartOrEndTurn(View view) {
        String buttonStringValue = restartEndTurnButton.getText().toString();
        switch (buttonStringValue) {
            case "Start":
                nextTurn(game);
                restartEndTurnButton.setText(R.string.restart);
                adapter.notifyItemChanged(game.size());
                break;
            case "Restart":
                dialogSwitch = 1;
                RestartGameDialogFragment dialogFragment = new RestartGameDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), null);
                break;
            default:
                turnsLeft--;
                turnsLeftAsString = String.format(res.getString(R.string.turns_left), turnsLeft);
                turnsTextView.setText(turnsLeftAsString);
                ArrayList<Integer> playerGuess = dldc.getPlayerNumbers();
                ArrayList<Integer> guessResult = com.compareNumbers(masterNumbers, playerGuess);
                boolean didPlayerWon = won.checkIfWon(guessResult);
                SingleTurn updatedActiveTurn = showResult(guessResult, game.get(indexOfActiveTurn));
                game.set(indexOfActiveTurn, updatedActiveTurn);
                adapter.notifyDataSetChanged();
                if (didPlayerWon) {
                    Toast.makeText(this, "YOU WON", Toast.LENGTH_SHORT).show();
//                //Alert YOU WON
                } else if (turnsLeft > 0) {
                    ng.resetDefaultNumbers();
                    dldc.resetPlayerNumbers();
                    restartEndTurnButton.setText(R.string.restart);
                    int color = ContextCompat.getColor(restartEndTurnButton.getContext(), R.color.gold);
                    restartEndTurnButton.setTextColor(color);
                    nextTurn(game);
                    indexOfActiveTurn = game.size() - 1;
                    adapter.setIndexOfActiveTurn(indexOfActiveTurn);
                } else {
//                //Alert YOU LOST - Back to menu / restart
                    Toast.makeText(this, "YOU LOST", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void nextTurn(ArrayList<SingleTurn> list) {
        final int ECG = R.drawable.empty_circle_golden;
        SingleTurn nextTurn = new SingleTurn(
                ECG, ECG, ECG, ECG, ECG, ECG, ECG, ECG);
        list.add((nextTurn));
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

    @SuppressLint("ClickableViewAccessibility")
    private void invokeAllBallViewsAndTouchListeners() {
        ImageView blueBallImageView = findViewById(R.id.blue_ball);
        ImageView yellowBallImageView = findViewById(R.id.yellow_ball);
        ImageView redBallImageView = findViewById(R.id.red_ball);
        ImageView greenBallImageView = findViewById(R.id.green_ball);
        ImageView orangeBallImageView = findViewById(R.id.orange_ball);
        ImageView whiteBallImageView = findViewById(R.id.white_ball);

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
                if (dialogSwitch == 1) {
                    startActivity(getIntent());
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    public void backToMainMenu(View view) {
        dialogSwitch = 2;
        ExitToMainMenuDialogFragment dialogFragment = new ExitToMainMenuDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), null);
    }
}
