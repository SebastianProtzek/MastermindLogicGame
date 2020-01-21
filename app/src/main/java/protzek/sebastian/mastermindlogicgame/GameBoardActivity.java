package protzek.sebastian.mastermindlogicgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import protzek.sebastian.mastermindlogicgame.Listeners.BallTouchListener;
import protzek.sebastian.mastermindlogicgame.Listeners.DragListenerDataCatcher;
import protzek.sebastian.mastermindlogicgame.Math.Comparator;
import protzek.sebastian.mastermindlogicgame.Math.NumbersGenerator;
import protzek.sebastian.mastermindlogicgame.Math.WonOrNot;

public class GameBoardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        invokeAllBallViewsAndTouchListeners();


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<SingleTurn> game = new ArrayList<>();
        final GameBoardAdapter adapter = new GameBoardAdapter(game);
        recyclerView.setAdapter(adapter);
        letsPlay(game);
    }

    private void letsPlay(ArrayList<SingleTurn> game) {
        NumbersGenerator ng = new NumbersGenerator();
        Comparator com = new Comparator();
        DragListenerDataCatcher dlh = new DragListenerDataCatcher();
        WonOrNot won = new WonOrNot();


        List<Integer> masterNumbers = ng.getMasterNumbers();
        int turnsLeft = ng.getNumberOfTurns();
        boolean didPlayerWon;

        do {
            nextTurn(game);
            turnsLeft--;
            ArrayList<Integer> playerGuess = dlh.getPlayerNumbers();
            List<Integer> guessResult = com.compareNumbers(masterNumbers, playerGuess);
            //change 4 view according to results

            didPlayerWon = won.checkIfWon(guessResult);
            if (didPlayerWon) {
                //Alert YOU WON
            }
            ng.setDefaultNumbers();
        } while (turnsLeft > 0 && !didPlayerWon);
        if (!didPlayerWon) {
            //Alert YOU LOST - Back to menu / restart
        }
    }

    private void nextTurn(ArrayList<SingleTurn> list) {
        list.add(createNextTurn(list));
    }

    private SingleTurn createNextTurn(ArrayList<SingleTurn> list) {
        int idCounter = list.size();
        SingleTurn nextTurn = new SingleTurn(
                R.drawable.empty_circle_golden,
                R.drawable.empty_circle_golden,
                R.drawable.empty_circle_golden,
                R.drawable.empty_circle_golden);
        return nextTurn;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void invokeAllBallViewsAndTouchListeners() {
        ImageView blueBallImageView = findViewById(R.id.blue_ball);
        ImageView yellowBallImageView = findViewById(R.id.yellow_ball);
        ImageView redBallImageView = findViewById(R.id.red_ball);
        ImageView greenBallImageView = findViewById(R.id.green_ball);
        ImageView orangeBallImageView = findViewById(R.id.orange_ball);
        ImageView whiteBallImageView = findViewById(R.id.white_ball);
        blueBallImageView.setOnTouchListener(new BallTouchListener());
        yellowBallImageView.setOnTouchListener(new BallTouchListener());
        redBallImageView.setOnTouchListener(new BallTouchListener());
        greenBallImageView.setOnTouchListener(new BallTouchListener());
        orangeBallImageView.setOnTouchListener(new BallTouchListener());
        whiteBallImageView.setOnTouchListener(new BallTouchListener());
    }
}
