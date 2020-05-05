package protzek.sebastian.mastermindlogicgame.mainmenu.highscores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.R;

public class HighScoresAdapter extends RecyclerView.Adapter<HighScoresAdapter.HighScoresViewHolder> {
    private ArrayList<HighScore> highScores;

    public HighScoresAdapter(ArrayList<HighScore> highScores) {
        this.highScores = highScores;
    }

    @NonNull
    @Override
    public HighScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.high_score, parent, false);
        return new HighScoresViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoresViewHolder holder, int position) {
        HighScore highScore = highScores.get(position);
        holder.placeInHighScoresTextView.setText(highScore.getPlace());
        holder.nicknameTextView.setText(highScore.getNickname());
        holder.scoreTextView.setText(highScore.getScore());
    }

    @Override
    public int getItemCount() {
        return highScores.size();
    }

    static class HighScoresViewHolder extends RecyclerView.ViewHolder{
        private TextView placeInHighScoresTextView;
        private TextView nicknameTextView;
        private TextView scoreTextView;

        public HighScoresViewHolder(@NonNull View itemView) {
            super(itemView);
            placeInHighScoresTextView = itemView.findViewById(R.id.place_in_high_scores_text_view);
            nicknameTextView = itemView.findViewById(R.id.nickname_text_view);
            scoreTextView = itemView.findViewById(R.id.score_text_view);
        }
    }
}
