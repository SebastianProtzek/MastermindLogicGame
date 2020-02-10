package protzek.sebastian.mastermindlogicgame.Enums;

import protzek.sebastian.mastermindlogicgame.R;

public enum Ball {
    BLUE(1, R.id.blue_ball, R.drawable.ball_blue),
    YELLOW(2, R.id.yellow_ball, R.drawable.ball_yellow),
    RED(3, R.id.red_ball, R.drawable.ball_red),
    GREEN(4, R.id.green_ball, R.drawable.ball_green),
    ORANGE(5, R.id.orange_ball, R.drawable.ball_orange),
    WHITE(6, R.id.white_ball, R.drawable.ball_white);

    private final int ballNumber;
    private final int id;
    private final int imageResource;

    Ball(int ballNumber, int id, int imageResource) {
        this.ballNumber = ballNumber;
        this.id = id;
        this.imageResource = imageResource;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public int getId() {
        return id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public static Ball fromId(int id) {
        Ball[] values = values();
        for (Ball ball : values) {
            if (ball.getId() == id)
                return ball;
        }
        return null;
    }
}
