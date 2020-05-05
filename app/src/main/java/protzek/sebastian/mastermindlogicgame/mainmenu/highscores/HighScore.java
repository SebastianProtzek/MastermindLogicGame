package protzek.sebastian.mastermindlogicgame.mainmenu.highscores;

public class HighScore {
    private String place;
    private String nickname;
    private String score;

    public HighScore(String place, String nickname, String score) {
        this.place = place;
        this.nickname = nickname;
        this.score = score;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
