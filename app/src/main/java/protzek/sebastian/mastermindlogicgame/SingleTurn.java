package protzek.sebastian.mastermindlogicgame;

public class SingleTurn {

    private int firstBall;
    private int secondBall;
    private int thirdBall;
    private int fourthBall;

    public SingleTurn(int firstBall, int secondBall, int thirdBall, int fourthBall) {
        this.firstBall = firstBall;
        this.secondBall = secondBall;
        this.thirdBall = thirdBall;
        this.fourthBall = fourthBall;
    }

    public int getFirstBall() {
        return firstBall;
    }

    public void setFirstBall(int firstBall) {
        this.firstBall = firstBall;
    }

    public int getSecondBall() {
        return secondBall;
    }

    public void setSecondBall(int secondBall) {
        this.secondBall = secondBall;
    }

    public int getThirdBall() {
        return thirdBall;
    }

    public void setThirdBall(int thirdBall) {
        this.thirdBall = thirdBall;
    }

    public int getFourthBall() {
        return fourthBall;
    }

    public void setFourthBall(int fourthBall) {
        this.fourthBall = fourthBall;
    }
}
