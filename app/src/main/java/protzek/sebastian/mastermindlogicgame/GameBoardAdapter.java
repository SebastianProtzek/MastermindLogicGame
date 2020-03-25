package protzek.sebastian.mastermindlogicgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.Listeners.BallDragListener;
import protzek.sebastian.mastermindlogicgame.Listeners.DragListenerDataCatcher;

public class GameBoardAdapter extends RecyclerView.Adapter<GameBoardAdapter.GameBoardViewHolder> {
    private ArrayList<SingleTurn> game;
    private DragListenerDataCatcher dldc;
    private int indexOfActiveTurn;

    GameBoardAdapter(ArrayList<SingleTurn> game, DragListenerDataCatcher dldc, int indexOfActiveTurn) {
        this.game = game;
        this.dldc = dldc;
        this.indexOfActiveTurn = indexOfActiveTurn;
    }

    @NonNull
    @Override
    public GameBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_turn, parent, false);
        return new GameBoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameBoardViewHolder holder, int position) {
        SingleTurn singleTurn = game.get(position);
        BallDragListener bdl = new BallDragListener(indexOfActiveTurn, position, dldc, singleTurn);
        holder.imageViewFirstBall.setImageResource(singleTurn.getFirstBall());
        holder.imageViewSecondBall.setImageResource(singleTurn.getSecondBall());
        holder.imageViewThirdBall.setImageResource(singleTurn.getThirdBall());
        holder.imageViewFourthBall.setImageResource(singleTurn.getFourthBall());
        holder.imageViewFirstScorePin.setImageResource(singleTurn.getFirstScorePin());
        holder.imageViewSecondScorePin.setImageResource(singleTurn.getSecondScorePin());
        holder.imageViewThirdScorePin.setImageResource(singleTurn.getThirdScorePin());
        holder.imageViewFourthScorePin.setImageResource(singleTurn.getFourthScorePin());
        holder.imageViewFirstBall.setOnDragListener(bdl);
        holder.imageViewSecondBall.setOnDragListener(bdl);
        holder.imageViewThirdBall.setOnDragListener(bdl);
        holder.imageViewFourthBall.setOnDragListener(bdl);
    }

    @Override
    public int getItemCount() {
        return game.size();
    }

    static class GameBoardViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewFirstBall;
        private ImageView imageViewSecondBall;
        private ImageView imageViewThirdBall;
        private ImageView imageViewFourthBall;
        private ImageView imageViewFirstScorePin;
        private ImageView imageViewSecondScorePin;
        private ImageView imageViewThirdScorePin;
        private ImageView imageViewFourthScorePin;

        GameBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFirstBall = itemView.findViewById(R.id.firstEmptySlot);
            imageViewSecondBall = itemView.findViewById(R.id.secondEmptySlot);
            imageViewThirdBall = itemView.findViewById(R.id.thirdEmptySlot);
            imageViewFourthBall = itemView.findViewById(R.id.fourthEmptySlot);
            imageViewFirstScorePin = itemView.findViewById(R.id.firstScorePin);
            imageViewSecondScorePin = itemView.findViewById(R.id.secondScorePin);
            imageViewThirdScorePin = itemView.findViewById(R.id.thirdScorePin);
            imageViewFourthScorePin = itemView.findViewById(R.id.fourthScorePin);
        }
    }

    void setIndexOfActiveTurn(int indexOfActiveTurn) {
        this.indexOfActiveTurn = indexOfActiveTurn;
    }
}