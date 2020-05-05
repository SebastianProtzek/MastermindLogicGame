package protzek.sebastian.mastermindlogicgame.mainmenu.highscores;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.Preferences;

public class HighScoresListBuilder {
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public HighScoresListBuilder(Context context, SharedPreferences prefs) {
        this.context = context;
        this.prefs = prefs;
        editor = prefs.edit();
        editor.apply();
    }

    public ArrayList<HighScore> createHighScoresList() {
        ArrayList<HighScore> highScores = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            String place = "" + i + ".";
            String nickname = createNickname(i);
            String score = createScore(i);
            HighScore highScore = new HighScore(place, nickname, score);
            highScores.add(highScore);
        }
        return highScores;
    }

    private String createNickname(int i) {
        String nicknameKey = "top" + i + "_n_key";
        String nickname = prefs.getString(nicknameKey, Preferences.NICKNAME_DEFAULT);
        if(nickname.equals(Preferences.NICKNAME_DEFAULT))
            nickname = "-";
        return nickname;
    }

    private String createScore(int i) {
        String scoreKey = "top" + i + "_s_key";
        int score = prefs.getInt(scoreKey, Preferences.SCORE_DEFAULT);
        int wonInTurn = score / 100;
        int numberOfTurns = score % 100;
        String scoreAsString;
        if (score == Preferences.SCORE_DEFAULT)
            scoreAsString = "-";
        else
            scoreAsString = String.format(context.getResources().getString(R.string.turns_left), wonInTurn, numberOfTurns);
        return scoreAsString;
    }

    public void updateHighScores() {
        editor = prefs.edit();
        int playerScore = prefs.getInt(context.getResources().getString(R.string.player_score_key), Preferences.SCORE_DEFAULT);
        String playerNickname = prefs.getString(context.getResources().getString(R.string.player_nickname_key), Preferences.NICKNAME_DEFAULT);
        for (int i = 10; i > 0; i--) {
            String scoreKey = "top" + i + "_s_key";
            int score = prefs.getInt(scoreKey, Preferences.SCORE_DEFAULT);
            String nicknameKey = "top" + i + "_n_key";
            String nickname = prefs.getString(nicknameKey, Preferences.NICKNAME_DEFAULT);

            if (playerScore < score) {
                editor.putInt(scoreKey, playerScore);
                editor.putString(nicknameKey, playerNickname);
                String oldHighScore = "top" + (i + 1) + "_s_key";
                editor.putInt(oldHighScore, score);
                String oldNickname = "top" + (i + 1) + "_n_key";
                editor.putString(oldNickname, nickname);
            }
        }
        editor.apply();
    }

    void resetHighScores() {
        for (int i = 1; i < 12; i++) {
            String scoreKey = "top" + i + "_s_key";
            editor.putInt(scoreKey, Preferences.SCORE_DEFAULT);
            String nicknameKey = "top" + i + "_n_key";
            editor.putString(nicknameKey, Preferences.NICKNAME_DEFAULT);
        }
        editor.apply();
    }
}
