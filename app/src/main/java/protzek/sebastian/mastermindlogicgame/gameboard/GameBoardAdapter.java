package protzek.sebastian.mastermindlogicgame.gameboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.gameboard.listeners.BallDragListener;
import protzek.sebastian.mastermindlogicgame.gameboard.listeners.DragListenerDataCatcher;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class GameBoardAdapter extends RecyclerView.Adapter<GameBoardAdapter.GameBoardViewHolder> {
    private DragListenerDataCatcher dldc;
    private SoundPlayer soundPlayer;
    private ArrayList<SingleTurn> game;
    private int indexOfActiveTurn;

    GameBoardAdapter(DragListenerDataCatcher dldc, SoundPlayer soundPlayer, ArrayList<SingleTurn> game, int indexOfActiveTurn) {
        this.dldc = dldc;
        this.soundPlayer = soundPlayer;
        this.game = game;
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
        BallDragListener bdl = new BallDragListener(dldc, soundPlayer, singleTurn, indexOfActiveTurn, position);
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

    void setIndexOfActiveTurn(int indexOfActiveTurn) {
        this.indexOfActiveTurn = indexOfActiveTurn;
    }

    ImageView getImageView(@NonNull GameBoardViewHolder holder) {
        return holder.imageViewFirstScorePin;
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

        public ImageView getImageViewFirstScorePin() {
            return imageViewFirstScorePin;
        }
    }
}