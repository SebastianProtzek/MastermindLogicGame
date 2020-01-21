package protzek.sebastian.mastermindlogicgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.Listeners.BallDragListener;

public class GameBoardAdapter extends RecyclerView.Adapter<GameBoardAdapter.GameBoardViewHolder> {
    private ArrayList<SingleTurn> gameTurns;

    public GameBoardAdapter(ArrayList<SingleTurn> gameTurns) {
        this.gameTurns = gameTurns;
    }

    @NonNull
    @Override
    public GameBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_turn, parent, false);
        return new GameBoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameBoardViewHolder holder, int position) {
        SingleTurn currentTurn = gameTurns.get(position);
        holder.imageViewFirstBall.setImageResource(currentTurn.getFirstBall());
        holder.imageViewSecondBall.setImageResource(currentTurn.getSecondBall());
        holder.imageViewThirdBall.setImageResource(currentTurn.getThirdBall());
        holder.imageViewFourthBall.setImageResource(currentTurn.getFourthBall());
        holder.imageViewFirstBall.setOnDragListener(new BallDragListener());
        holder.imageViewSecondBall.setOnDragListener(new BallDragListener());
        holder.imageViewThirdBall.setOnDragListener(new BallDragListener());
        holder.imageViewFourthBall.setOnDragListener(new BallDragListener());
    }

    @Override
    public int getItemCount() {
        return gameTurns.size();
    }

    class GameBoardViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewFirstBall;
        private ImageView imageViewSecondBall;
        private ImageView imageViewThirdBall;
        private ImageView imageViewFourthBall;

        public GameBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFirstBall = itemView.findViewById(R.id.firstBall);
            imageViewSecondBall = itemView.findViewById(R.id.secondBall);
            imageViewThirdBall = itemView.findViewById(R.id.thirdBall);
            imageViewFourthBall = itemView.findViewById(R.id.fourthBall);
        }
    }
}