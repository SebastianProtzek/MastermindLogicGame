package protzek.sebastian.mastermindlogicgame.Enums;

import protzek.sebastian.mastermindlogicgame.R;

public enum Ball {
    BLUE(1, R.id.blue_ball_image_view, R.drawable.ball_blue),
    YELLOW(2, R.id.yellow_ball_image_view, R.drawable.ball_yellow),
    WHITE(3, R.id.white_ball_image_view, R.drawable.ball_white),
    GREEN(4, R.id.green_ball_image_view, R.drawable.ball_green),
    ORANGE(5, R.id.orange_ball_image_view, R.drawable.ball_orange),
    RED(6, R.id.red_ball_image_view, R.drawable.ball_red);

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

    public static Ball fromBallNumber(int ballNumber) {
        Ball[] values = values();
        for (Ball ball : values) {
            if (ball.getBallNumber() == ballNumber)
                return ball;
        }
        return null;
    }
}
